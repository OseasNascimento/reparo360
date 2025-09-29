package br.com.reparo360.estoque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
@Table(name = "produtos")

public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    @Min(value = 0, message = "Quantidade de estoque não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @NotNull(message = "Valor do produto é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "Valor não pode ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;


}
