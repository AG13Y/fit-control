package com.fc.fit_control.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UsuarioUpdateDTO(
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotEmpty Set<Role> roles
) {
}
