package br.com.reparo360.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "servicos")


public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Servico_id")
    private Long idServico;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 255, message = "Descrição não pode exceder 255 caracteres")
    @Column(name = "descricao")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 255, message = "Categoria não pode exceder 255 caracteres")
    @Column(name = "categoria")
    private String categoria;

    @NotNull(message = "Valor estimado é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser positivo")
    @Column(name = "valor_estimado")
    private Double valorEstimado;

    @NotNull(message = "Tempo estimado é obrigatório")
    @Min(value = 1, message = "Tempo deve ser ao menos 1")
    @Column(name = "tempo_estimado")
    private Integer tempoEstimado;

    @ManyToMany(mappedBy = "servicos")
    @EqualsAndHashCode.Exclude
    private Set<Agendamento> agendamentos = new HashSet<>();

}
