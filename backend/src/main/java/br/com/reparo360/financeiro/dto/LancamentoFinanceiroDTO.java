package br.com.reparo360.financeiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "LancamentoFinanceiroDTO", description = "Dados de um lançamento financeiro")
public class LancamentoFinanceiroDTO {

    @Schema(description = "Identificador do lançamento", example = "1")
    private Long id;

    @NotNull(message = "ContaCaixa é obrigatória")
    @Schema(description = "ID da conta caixa associada", example = "1", required = true)
    private Long contaCaixaId;

    @NotNull(message = "CategoriaFinanceira é obrigatória")
    @Schema(description = "ID da categoria financeira associada", example = "2", required = true)
    private Long categoriaFinanceiraId;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser maior que zero")
    @Schema(description = "Valor do lançamento", example = "150.50", required = true)
    private BigDecimal valor;

    @Schema(description = "Data e hora do lançamento", example = "2025-06-12T17:30:00")
    private LocalDateTime dataLancamento;

    @NotNull(message = "TipoTransacao é obrigatório")
    @Schema(description = "Tipo de transação (RECEITA ou DESPESA)", example = "RECEITA", required = true)
    private String tipoTransacao;

    @Schema(description = "Descrição complementar do lançamento", example = "Pagamento de serviço de manutenção")
    private String descricao;
}
