package com.fc.fit_control.service;

import com.fc.fit_control.dto.UsuarioRequestDTO;
import com.fc.fit_control.dto.UsuarioResponseDTO;
import com.fc.fit_control.dto.UsuarioUpdateDTO;
import com.fc.fit_control.entity.Usuario;
import com.fc.fit_control.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void criarUsuario(UsuarioRequestDTO dto) {

        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setRoles(dto.roles());
        usuario.setAcademyId(1L); // temporário ( melhorar isso depois)

        usuarioRepository.save(usuario);
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return mapToResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        // 1. Verificar se o utilizador existe
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));

        // 2. Regra de negócio: Se o email mudou, verificar se o novo email já está em uso
        if (!usuario.getEmail().equals(dto.email()) &&
                usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Este email já está a ser utilizado por outro utilizador");
        }

        // 3. Atualizar os campos permitidos
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setRoles(dto.roles());

        // 4. Guardar as alterações
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        // 5. Retornar o DTO de resposta (reutilizando a lógica de mapeamento)
        return mapToResponseDTO(usuarioAtualizado);
    }

    @Transactional // Garante que a operação seja confirmada no banco
    public void excluirUsuario(Long id) {
        // Verifica se existe antes de tentar apagar
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado com o ID: " + id));

        // Se tiveres relações complexas, podes precisar de limpar coleções manualmente
        // No caso do Usuario.java, as roles são @ElementCollection, então o JPA deve cuidar disso

        usuarioRepository.delete(usuario);
    }

    // Helper method para conversão (Poderia usar ModelMapper ou MapStruct no futuro)
    private UsuarioResponseDTO mapToResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRoles(),
                usuario.getAtivo()
        );
    }


}
