package br.com.reparo360.estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ProdutoDTO", description = "Dados de um produto em estoque")
public class ProdutoDTO {

    @Schema(description = "Identificador do produto", example = "1")
    private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Schema(description = "Nome do produto", example = "Cabo de alimentação", required = true)
    private String nome;

    @Schema(description = "Descrição opcional do produto", example = "Cabo de alimentação de 1,5m")
    private String descricao;

    @Min(value = 0, message = "Quantidade de estoque não pode ser negativa")
    @Schema(description = "Quantidade disponível em estoque", example = "10", required = true)
    private Integer quantidadeEstoque;

    @NotNull(message = "Valor do produto é obrigatório")
    @Schema(description = "Valor do produto", example = "35,00", required = true)
    @DecimalMin(value = "0.0", inclusive = true, message = "Valor não pode ser negativo")
    private BigDecimal valor;

}
