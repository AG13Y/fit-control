package com.fc.fit_control.controller;

import com.fc.fit_control.dto.UsuarioRequestDTO;
import com.fc.fit_control.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
