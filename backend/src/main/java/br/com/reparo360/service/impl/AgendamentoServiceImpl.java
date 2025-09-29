package br.com.reparo360.service.impl;

import br.com.reparo360.dto.AgendamentoDTO;
import br.com.reparo360.entity.Agendamento;
import br.com.reparo360.entity.EnderecoAgendamento;
import br.com.reparo360.entity.Servico;
import br.com.reparo360.entity.StatusAgendamento;
import br.com.reparo360.ordemservico.entity.OrdemServico;
import br.com.reparo360.ordemservico.entity.StatusOrdemServico;
import br.com.reparo360.exception.DuplicateBookingException;
import br.com.reparo360.mapper.AgendamentoMapper;
import br.com.reparo360.repository.AgendamentoRepository;
import br.com.reparo360.repository.ClienteRepository;
import br.com.reparo360.repository.ServicoRepository;
import br.com.reparo360.repository.TecnicoRepository;
import br.com.reparo360.ordemservico.repository.OrdemServicoRepository;
import br.com.reparo360.service.AgendamentoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository repo;
    private final ClienteRepository clienteRepo;
    private final TecnicoRepository tecnicoRepo;
    private final ServicoRepository servicoRepo;
    private final OrdemServicoRepository ordemServicoRepository;
    private final AgendamentoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<LocalDateTime> findOccupiedSlots(Long tecnicoId, LocalDate dia) {
        LocalDateTime start = dia.atStartOfDay();
        LocalDateTime end   = dia.atTime(LocalTime.MAX);
        return repo.findByTecnicoIdAndDataAgendamentoBetween(tecnicoId, start, end)
                .stream()
                .map(Agendamento::getDataAgendamento)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AgendamentoDTO create(AgendamentoDTO dto) {
        // 1) Converte DTO → Entidade (campos básicos + endereço)
        Agendamento entity = mapper.toEntity(dto);

        LocalDateTime dt = dto.getDataAgendamento();
        Long techId = dto.getTecnicoId();
        if (repo.existsByTecnicoIdAndDataAgendamento(techId, dt)) {
            throw new DuplicateBookingException(
                    "Este técnico já possui agendamento em " +
                            dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );
        }

        // 2) Cliente
        entity.setCliente(
                clienteRepo.findById(dto.getClienteId())
                        .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"))
        );

        // 3) Técnico
        entity.setTecnico(
                tecnicoRepo.findById(dto.getTecnicoId())
                        .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado"))
        );

        // 4) Serviços (validação e conversão para Set)
        List<Long> ids = dto.getServicosId();
        List<Servico> lista = servicoRepo.findAllById(ids);
        if (lista.size() != ids.size()) {
            Set<Long> encontrados = lista.stream()
                    .map(Servico::getIdServico)
                    .collect(Collectors.toSet());
            List<Long> faltantes = ids.stream()
                    .filter(id -> !encontrados.contains(id))
                    .collect(Collectors.toList());
            throw new EntityNotFoundException(
                    "Serviço(s) não encontrado(s) para ID(s): " + faltantes
            );
        }
        entity.setServicos(new HashSet<>(lista));

        // 5) Endereço
        EnderecoAgendamento endereco = mapper.toEnderecoEntity(dto);
        entity.setEnderecoAgendamento(endereco);

        // 6) Salva Agendamento
        Agendamento salvo = repo.save(entity);

        // 7) Criação automática da OS básica
        OrdemServico os = new OrdemServico();
        os.setAgendamento(salvo);
        os.setStatus(StatusOrdemServico.AGENDADA);
        os.setValorServico(BigDecimal.ZERO);
        os.setValorMateriais(BigDecimal.ZERO);
        os.setKmDeslocamento(0);

        EnderecoAgendamento end = salvo.getEnderecoAgendamento();
        os.setLogradouro(  end.getLogradouro());
        os.setNumero(      end.getNumero());
        os.setComplemento( end.getComplemento());
        os.setBairro(      end.getBairro());
        os.setCidade(      end.getCidade());
        os.setUf(          end.getUf());
        os.setCep(         end.getCep());

        ordemServicoRepository.save(os);

        // 8) Retorna DTO
        return mapper.toDTO(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AgendamentoDTO findById(Long id) {
        Agendamento a = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        return mapper.toDTO(a);
    }

    @Override
    @Transactional
    public AgendamentoDTO update(Long id, AgendamentoDTO dto) {
        // 1) Verifica existência
        repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        // 2) Mapeia DTO → Entidade (sem relações)
        Agendamento updated = mapper.toEntity(dto);
        updated.setIdAgendamento(id);

        // 3) Cliente e Técnico
        updated.setCliente(
                clienteRepo.findById(dto.getClienteId())
                        .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"))
        );
        updated.setTecnico(
                tecnicoRepo.findById(dto.getTecnicoId())
                        .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado"))
        );

        // 4) Serviços
        List<Long> idsUpdate = dto.getServicosId();
        List<Servico> listaUpdate = servicoRepo.findAllById(idsUpdate);
        if (listaUpdate.size() != idsUpdate.size()) {
            Set<Long> encontrados = listaUpdate.stream()
                    .map(Servico::getIdServico)
                    .collect(Collectors.toSet());
            List<Long> faltantes = idsUpdate.stream()
                    .filter(i -> !encontrados.contains(i))
                    .collect(Collectors.toList());
            throw new EntityNotFoundException(
                    "Serviço(s) não encontrado(s) para ID(s): " + faltantes
            );
        }
        updated.setServicos(new HashSet<>(listaUpdate));

        // 5) Endereço
        EnderecoAgendamento enderecoUpdate = mapper.toEnderecoEntity(dto);
        updated.setEnderecoAgendamento(enderecoUpdate);

        // 6) Persiste atualização
        Agendamento salvo = repo.save(updated);

        // 7) Retorna DTO
        return mapper.toDTO(salvo);
    }

    @Override
    @Transactional
    public AgendamentoDTO updateStatus(Long id, String status) {
        Agendamento a = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        a.setStatus(StatusAgendamento.valueOf(status));
        repo.save(a);
        return mapper.toDTO(a);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado para remoção: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> historyByCliente(Long clienteId) {
        return repo.findByClienteIdCliente(clienteId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> historyByTecnico(Long tecnicoId) {
        return repo.findByTecnicoId(tecnicoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
