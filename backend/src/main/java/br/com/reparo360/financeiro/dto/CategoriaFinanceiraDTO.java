package br.com.reparo360.financeiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CategoriaFinanceiraDTO", description = "Dados de uma categoria financeira")
public class CategoriaFinanceiraDTO {

    @Schema(description = "Identificador da categoria", example = "1")
    private Long id;

    @NotBlank(message = "Nome da categoria é obrigatório")
    @Schema(description = "Nome da categoria financeira", example = "Manutenção", required = true)
    private String nome;

    @Schema(description = "Descrição opcional da categoria", example = "Serviços de manutenção e reparo")
    private String descricao;
}
