package br.com.reparo360.financeiro.entity;

import br.com.reparo360.ordemservico.entity.OrdemServico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "apuracoes_servico")


public class ApuracaoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "OrdemServico é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;

    @NotNull(message = "Valor total do serviço é obrigatório")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorServico;

    @NotNull(message = "Valor total dos materiais é obrigatório")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorMateriais;

    @NotNull(message = "Deslocamento (km) é obrigatório")
    @Column(nullable = false)
    private Integer kmDeslocamento;

    @NotNull(message = "Receita líquida é obrigatória")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal receitaLiquida;

    @NotNull(message = "Data de apuração é obrigatória")
    @Column(nullable = false)
    private LocalDateTime dataApuracao;


}
