package br.com.reparo360.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Resumo com os principais indicadores do dashboard",
        // opcional: caso queira validar no nível de classe
        requiredProperties = { "receita", "despesa", "lucro", "totalServicos" }
)
public class ResumoDashboardDTO {

    @Schema(
            description = "Receita total em reais",
            example     = "12345.67",
            required    = true
    )
    private Double receita;

    @Schema(
            description = "Despesa total em reais",
            example     = "2345.67",
            required    = true
    )
    private Double despesa;

    @Schema(
            description = "Lucro total em reais",
            example     = "10000.00",
            required    = true
    )
    private Double lucro;

    @Schema(
            description = "Total de serviços realizados",
            example     = "42",
            required    = true
    )
    private Long totalServicos;
}


