package br.com.reparo360.financeiro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "contas_caixa")


public class ContaCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da conta é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    @NotNull(message = "Saldo inicial é obrigatório")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoInicial;


}

