package br.com.reparo360.ordemservico.dto;

import br.com.reparo360.ordemservico.entity.StatusOrdemServico;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OrdemServicoDTO", description = "Dados de uma ordem de serviço")
public class OrdemServicoDTO {

    @Schema(description = "Identificador da ordem de serviço", example = "1")
    private Long id;

    @NotNull(message = "AgendamentoId é obrigatório")
    @Schema(description = "ID do agendamento associado", example = "5", required = true)
    private Long agendamentoId;

    // ─── Endereço herdado do Agendamento ─────────────────────────────────────
    @Schema(description = "Logradouro do endereço do cliente", example = "Rua das Flores")
    private String logradouro;

    @Schema(description = "Número do endereço do cliente", example = "123")
    private String numero;

    @Schema(description = "Complemento do endereço do cliente", example = "Apto 45")
    private String complemento;

    @Schema(description = "Bairro do endereço do cliente", example = "Centro")
    private String bairro;

    @Schema(description = "Cidade do endereço do cliente", example = "São Paulo")
    private String cidade;

    @Schema(description = "UF do endereço do cliente", example = "SP")
    private String uf;

    @Schema(description = "CEP do endereço do cliente", example = "01234-567")
    private String cep;
    // ──────────────────────────────────────────────────────────────────────────

    @NotNull(message = "Status é obrigatório")
    @Schema(description = "Status da ordem de serviço", example = "ABERTA", required = true)
    private StatusOrdemServico status;

    @NotNull(message = "Valor do serviço é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "Valor do serviço não pode ser negativo")
    @Schema(description = "Valor cobrado pelo serviço", example = "250.00", required = true)
    private BigDecimal valorServico;

    @NotNull(message = "Valor dos materiais é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "Valor dos materiais não pode ser negativo")
    @Schema(description = "Custo dos materiais utilizados", example = "50.00", required = true)
    private BigDecimal valorMateriais;

    @NotNull(message = "KM de deslocamento é obrigatório")
    @Min(value = 0, message = "Quilometragem não pode ser negativa")
    @Schema(description = "Quilometragem percorrida para o serviço", example = "10", required = true)
    private Integer kmDeslocamento;

    @Schema(description = "Observações adicionais da ordem de serviço", example = "Cliente pediu rapidez")
    private String observacoes;
}
