package br.com.reparo360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ServicoDTO", description = "Dados de um serviço oferecido pelo sistema")
public class ServicoDTO {

    @Schema(description = "Identificador do serviço", example = "1")
    private Long idServico;

    @NotBlank(message = "Descrição do serviço é obrigatória")
    @Size(max = 255, message = "Descrição não pode exceder 255 caracteres")
    @Schema(description = "Descrição detalhada do serviço", example = "Manutenção preventiva de motor", required = true)
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 255, message = "Categoria não pode exceder 255 caracteres")
    @Schema(description = "Categoria à qual o serviço pertence", example = "Refrigeração", required = true)
    private String categoria;

    @NotNull(message = "Valor estimado é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser positivo")
    @Schema(description = "Valor estimado do serviço", example = "150.0", required = true)
    private Double valorEstimado;

    @NotNull(message = "Tempo estimado é obrigatório")
    @Min(value = 1, message = "Tempo deve ser ao menos 1")
    @Schema(description = "Tempo estimado para conclusão do serviço (em minutos)", example = "60", required = true)
    private Integer tempoEstimado;
}
