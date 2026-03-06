package com.fc.fit_control.entity;

import com.fc.fit_control.dto.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private Long academyId;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "role")
    private Set<Role> roles;

    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }

}
