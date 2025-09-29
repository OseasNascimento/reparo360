package br.com.reparo360.financeiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ApuracaoServicoDTO", description = "Dados de apuração financeira de um serviço")
public class ApuracaoServicoDTO {

    @Schema(description = "Identificador da apuração", example = "1")
    private Long id;

    @Schema(description = "ID da Ordem de Serviço associada", example = "100", required = true)
    private Long ordemServicoId;

    @Schema(description = "Valor total cobrado pelo serviço", example = "200.50", required = true)
    private BigDecimal valorServico;

    @Schema(description = "Valor total dos materiais utilizados", example = "50.75", required = true)
    private BigDecimal valorMateriais;

    @Schema(description = "Quilometragem de deslocamento para o serviço", example = "10", required = true)
    private Integer kmDeslocamento;

    @Schema(description = "Receita líquida após custos", example = "149.75", required = true)
    private BigDecimal receitaLiquida;

    @Schema(description = "Data e hora em que a apuração foi realizada", example = "2025-06-12T17:00:00", required = true)
    private LocalDateTime dataApuracao;
}
