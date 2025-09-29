// src/main/java/br/com/reparo360/config/dto/ValorDTO.java
package br.com.reparo360.config.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class ValorDto {
    @Schema(description = "Valor numérico", example = "1.50")
    private BigDecimal valor;
}
