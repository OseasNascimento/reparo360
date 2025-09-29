package br.com.reparo360.repository;

import br.com.reparo360.entity.Agendamento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByClienteIdCliente(Long clienteId);
    List<Agendamento> findByTecnicoId(Long tecnicoId);
    List<Agendamento> findByTecnicoIdAndDataAgendamentoBetween(Long tecnicoId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    boolean existsByTecnicoIdAndDataAgendamento(Long tecnicoId, LocalDateTime dataAgendamento);


        @Override
        @EntityGraph(attributePaths = {
                "servicos",
                "enderecoAgendamento",
                "cliente",
                "tecnico"
        })
        List<Agendamento> findAll();

        @Override
        @EntityGraph(attributePaths = {
                "servicos",
                "enderecoAgendamento",
                "cliente",
                "tecnico"
        })
        Optional<Agendamento> findById(Long id);
    }




