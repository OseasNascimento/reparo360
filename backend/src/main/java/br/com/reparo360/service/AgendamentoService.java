package br.com.reparo360.service;

import br.com.reparo360.dto.AgendamentoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AgendamentoService {
    AgendamentoDTO create(AgendamentoDTO dto);
    List<AgendamentoDTO> findAll();
    AgendamentoDTO findById(Long id);
    AgendamentoDTO update(Long id, AgendamentoDTO dto);
    AgendamentoDTO updateStatus(Long id, String novoStatus);
    List<LocalDateTime> findOccupiedSlots(Long tecnicoId, LocalDate dia);
    void delete(Long id);
    List<AgendamentoDTO> historyByCliente(Long clienteId);
    List<AgendamentoDTO> historyByTecnico(Long tecnicoId);
}
