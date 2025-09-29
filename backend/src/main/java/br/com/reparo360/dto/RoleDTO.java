package br.com.reparo360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "RoleDTO", description = "Dados de uma role de acesso")
public class RoleDTO {

    @Schema(description = "Identificador da role", example = "1")
    private Long idRole;

    @NotBlank(message = "Nome da role é obrigatório")
    @Size(max = 50, message = "Nome da role não pode exceder 50 caracteres")
    @Schema(description = "Nome técnico da role", example = "ADMIN", required = true)
    private String nomeRole;

    @Size(max = 100, message = "Descrição não pode exceder 100 caracteres")
    @Schema(description = "Descrição da finalidade da role", example = "Permissão de administrador do sistema")
    private String descricao;
}
