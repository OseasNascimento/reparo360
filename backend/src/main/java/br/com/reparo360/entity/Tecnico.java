package br.com.reparo360.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "tecnicos")


public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tecnico_id")
    private Long id;

    @NotBlank(message = "Nome do técnico é obrigatório")
    @Size(max = 255, message = "Nome não pode exceder 255 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email não pode exceder 255 caracteres")
    @Column(name = "email",unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 50, message = "Telefone não pode exceder 50 caracteres")
    @Column(name = "telefone")
    private String telefone;

    @NotBlank(message = "Especialidade é obrigatória")
    @Size(max = 255, message = "Especialidade não pode exceder 255 caracteres")
    @Column(name = "especialidade")
    private String especialidade;

    @Column(name = "data_contratacao")
    @NotNull(message = "Data de contratação é obrigatória")
    private LocalDateTime dataContratacao;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter ao menos 6 caracteres")
    @Column(name = "senha")
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tecnico_roles",
            joinColumns = @JoinColumn(name = "tecnico_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


}
