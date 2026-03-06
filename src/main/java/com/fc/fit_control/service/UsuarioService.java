package com.fc.fit_control.service;

import com.fc.fit_control.dto.UsuarioRequestDTO;
import com.fc.fit_control.entity.Usuario;
import com.fc.fit_control.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

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
}
