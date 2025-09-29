package br.com.reparo360.estoque.dto;

import br.com.reparo360.estoque.entity.TipoMovimentacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "MovimentacaoEstoqueDTO", description = "Dados de uma movimentação de estoque")
public class MovimentacaoEstoqueDTO {

    @Schema(description = "Identificador da movimentação de estoque", example = "1")
    private Long id;

    @NotNull(message = "Produto é obrigatório")
    @Schema(description = "ID do produto movimentado", example = "42", required = true)
    private Long produtoId;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    @Schema(description = "Tipo de movimentação (ENTRADA ou SAÍDA)", example = "ENTRADA", required = true)
    private TipoMovimentacao tipo;

    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    @Schema(description = "Quantidade de itens movimentados", example = "10", required = true)
    private Integer quantidade;

    @FutureOrPresent(message = "A data de movimentação não pode ser no passado")
    @Schema(description = "Data e hora da movimentação", example = "2025-06-12T16:00:00", required = true)
    private LocalDateTime dataMovimentacao;

    @Schema(description = "Descrição opcional sobre a movimentação", example = "Recebimento de novo lote")
    private String descricao;
}
