package com.fc.fit_control.controller;

import com.fc.fit_control.dto.UsuarioRequestDTO;
import com.fc.fit_control.dto.UsuarioResponseDTO;
import com.fc.fit_control.dto.UsuarioUpdateDTO;
import com.fc.fit_control.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody @Valid UsuarioRequestDTO dto) {

        usuarioService.criarUsuario(dto);

        return ResponseEntity.ok("Usuário criado com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> lista = usuarioService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioUpdateDTO dto) {

        UsuarioResponseDTO response = usuarioService.atualizarUsuario(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);

        // Retorna 204 No Content (padrão para exclusão com sucesso)
        return ResponseEntity.noContent().build();
    }
}
