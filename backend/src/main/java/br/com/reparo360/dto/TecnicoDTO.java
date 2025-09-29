package br.com.reparo360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "TecnicoDTO", description = "Dados de um técnico")
public class TecnicoDTO {

    @Schema(description = "Identificador do técnico", example = "1")
    private Long id;

    @NotBlank(message = "Nome do técnico é obrigatório")
    @Size(max = 255, message = "Nome não pode exceder 255 caracteres")
    @Schema(description = "Nome completo do técnico", example = "Carlos Pereira", required = true)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email não pode exceder 255 caracteres")
    @Schema(description = "E-mail de acesso do técnico", example = "carlos.pereira@example.com", required = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 50, message = "Telefone não pode exceder 50 caracteres")
    @Schema(description = "Telefone de contato do técnico", example = "(11) 91234-5678", required = true)
    private String telefone;

    @NotBlank(message = "Especialidade é obrigatória")
    @Size(max = 255, message = "Especialidade não pode exceder 255 caracteres")
    @Schema(description = "Especialidade do técnico", example = "Refrigeração", required = true)
    private String especialidade;

    @NotNull(message = "Data de contratação é obrigatória")
    @Schema(description = "Data de contratação do técnico", example = "2025-05-01T08:30:00", required = true)
    private LocalDateTime dataContratacao;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter ao menos 6 caracteres")
    @Schema(description = "Senha criptografada do técnico", required = true)
    private String senha;

    @Schema(description = "Conjunto de roles atribuídas ao técnico")
    private Set<RoleDTO> roles;
}
