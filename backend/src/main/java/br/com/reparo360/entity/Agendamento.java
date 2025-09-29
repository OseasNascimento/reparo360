package br.com.reparo360.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "agendamentos")


public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Long idAgendamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = false)
    @NotNull(message = "Técnico é obrigatório")
    private Tecnico tecnico;

    @Column(name = "data_agendamento")
    @NotNull(message = "Data de agendamento é obrigatória")
    private LocalDateTime dataAgendamento;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status é obrigatório")
    private StatusAgendamento status;

    @Size(max = 255, message = "Observações não podem exceder 255 caracteres")
    private String observacoes;

    @Embedded
    private EnderecoAgendamento enderecoAgendamento;

    @ManyToMany
    @JoinTable(
            name = "agendamento_servico",
            joinColumns = @JoinColumn(name = "agendamento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    @NotEmpty(message = "Selecione ao menos um serviço")
    private Set<Servico> servicos = new HashSet<>();
}

