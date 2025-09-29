package br.com.reparo360.config.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "parametros_config")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ParametroConfig {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String chave;

    @Column(name = "valor_numeric", precision = 19, scale = 2)
    private BigDecimal valorNumeric;

    @Column(name = "valor_texto", length = 255)
    private String valorTexto;
}
