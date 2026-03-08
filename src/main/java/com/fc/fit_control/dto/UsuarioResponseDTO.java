package com.fc.fit_control.dto;

import java.util.Set;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Set<Role> roles,
        Boolean ativo
) {
}
