package br.com.reparo360.financeiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ContaCaixaDTO", description = "Dados de uma conta caixa")
public class ContaCaixaDTO {

    @Schema(description = "Identificador da conta caixa", example = "1")
    private Long id;

    @NotBlank(message = "Nome da conta é obrigatório")
    @Schema(description = "Nome da conta caixa", example = "Caixa Principal", required = true)
    private String nome;

    @NotNull(message = "Saldo inicial é obrigatório")
    @Schema(description = "Saldo inicial da conta caixa", example = "1000.00", required = true)
    private BigDecimal saldoInicial;
}
