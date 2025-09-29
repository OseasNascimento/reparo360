package br.com.reparo360.ordemservico.entity;

import br.com.reparo360.entity.Agendamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordens_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Agendamento é obrigatório")
    @OneToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento;

    // ─── Endereço herdado do Agendamento ─────────────────────────────────────
    @Column(name = "logradouro", nullable = false, length = 255)
    private String logradouro;

    @Column(name = "numero", nullable = false, length = 50)
    private String numero;

    @Column(name = "complemento", length = 255)
    private String complemento;

    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @Column(name = "cep", nullable = false, length = 20)
    private String cep;
    // ──────────────────────────────────────────────────────────────────────────

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrdemServico status;

    @NotNull(message = "Valor do serviço é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "Valor do serviço não pode ser negativo")
    @Column(name = "valor_servico", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal valorServico = BigDecimal.ZERO;

    @NotNull(message = "Valor dos materiais é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "Valor dos materiais não pode ser negativo")
    @Column(name = "valor_materiais", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal valorMateriais = BigDecimal.ZERO;

    @NotNull(message = "KM de deslocamento é obrigatório")
    @Min(value = 0, message = "Quilometragem não pode ser negativa")
    @Column(name = "km_deslocamento", nullable = false)
    @Builder.Default
    private Integer kmDeslocamento = 0;

    @Column(length = 255)
    private String observacoes;

    // ─── Datas para métricas e relatórios ────────────────────────────────────
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_realizacao")
    private LocalDateTime dataRealizacao;
}
