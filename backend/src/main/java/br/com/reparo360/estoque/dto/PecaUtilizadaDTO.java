package br.com.reparo360.estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "PecaUtilizadaDTO", description = "Dados de uma peça utilizada em Ordem de Serviço")
public class PecaUtilizadaDTO implements Serializable {

    @Schema(description = "Identificador da peça utilizada", example = "1")
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Schema(description = "Descrição da peça utilizada", example = "Filtro de ar", required = true)
    private String descricao;

    @NotNull(message = "Produto é obrigatório")
    @Schema(description = "ID do produto relacionado", example = "42", required = true)
    private Long produtoId;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade mínima é 1")
    @Schema(description = "Quantidade de peças utilizadas", example = "2", required = true)
    private Integer quantidade;

    @NotNull(message = "Ordem de Serviço é obrigatória")
    @Schema(description = "ID da Ordem de Serviço à qual a peça está vinculada", example = "100", required = true)
    private Long ordemServicoId;
}
