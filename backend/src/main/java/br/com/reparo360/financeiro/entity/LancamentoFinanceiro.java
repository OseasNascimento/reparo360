package br.com.reparo360.financeiro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "lancamentos_financeiros")


public class LancamentoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // GETTER = getId()

    @NotNull(message = "ContaCaixa é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_caixa_id", nullable = false)
    private ContaCaixa contaCaixa;
    // ContaCaixa deve ter um getter público getId()

    @NotNull(message = "CategoriaFinanceira é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_financeira_id", nullable = false)
    private CategoriaFinanceira categoriaFinanceira;
    // CategoriaFinanceira deve ter um getter público getId()

    @NotNull(message = "Valor é obrigatório")
    @Column(name = "valor", nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;


    @Column(name = "data_lancamento", nullable = false)
    private LocalDateTime dataLancamento;

    @NotNull(message = "TipoTransacao é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", nullable = false)
    private TipoTransacao tipoTransacao; // enum RECEITA ou DESPESA

    private String descricao;


}
