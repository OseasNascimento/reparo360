package br.com.reparo360.ordemservico.service.impl;

import br.com.reparo360.entity.Agendamento;
import br.com.reparo360.entity.EnderecoAgendamento;
import br.com.reparo360.exception.ResourceNotFoundException;
import br.com.reparo360.ordemservico.dto.OrdemServicoDTO;
import br.com.reparo360.ordemservico.entity.OrdemServico;
import br.com.reparo360.ordemservico.entity.StatusOrdemServico;
import br.com.reparo360.ordemservico.event.OrdemServicoStatusChangedEvent;
import br.com.reparo360.ordemservico.mapper.OrdemServicoMapper;
import br.com.reparo360.ordemservico.repository.OrdemServicoRepository;
import br.com.reparo360.ordemservico.service.OrdemServicoService;
import br.com.reparo360.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdemServicoServiceImpl implements OrdemServicoService {

    private final OrdemServicoRepository osRepo;
    private final AgendamentoRepository agendamentoRepo;
    private final OrdemServicoMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public OrdemServicoDTO criarOrdem(Long agendamentoId) {
        // Se já existe OS para este agendamento, apenas retorna
        Optional<OrdemServico> existenteOpt =
                osRepo.findFirstByAgendamentoIdAgendamento(agendamentoId);
        if (existenteOpt.isPresent()) {
            return mapper.toDTO(existenteOpt.get());
        }

        Agendamento ag = agendamentoRepo.findById(agendamentoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Agendamento não encontrado para id " + agendamentoId));

        OrdemServico os = new OrdemServico();
        os.setAgendamento(ag);
        os.setStatus(StatusOrdemServico.AGENDADA);
        os.setValorServico(BigDecimal.ZERO);
        os.setValorMateriais(BigDecimal.ZERO);
        os.setKmDeslocamento(0);
        os.setObservacoes(ag.getObservacoes());

        // Snapshot do endereço do agendamento
        EnderecoAgendamento end = ag.getEnderecoAgendamento();
        if (end != null) {
            os.setLogradouro(end.getLogradouro());
            os.setNumero(end.getNumero());
            os.setComplemento(end.getComplemento());
            os.setBairro(end.getBairro());
            os.setCidade(end.getCidade());
            os.setUf(end.getUf());
            os.setCep(end.getCep());
        }

        os.setDataCriacao(LocalDateTime.now());

        OrdemServico salvo = osRepo.save(os);
        return mapper.toDTO(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public OrdemServicoDTO buscarPorId(Long id) {
        OrdemServico os = osRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "OrdemServico não encontrada para id " + id));
        return mapper.toDTO(os);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> listarTodas() {
        return osRepo.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> listarPorStatus(StatusOrdemServico status) {
        return osRepo.findByStatus(status).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrdemServicoDTO atualizarOrdem(Long id, OrdemServicoDTO dto) {
        OrdemServico existente = osRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "OrdemServico não encontrada para id " + id));

        StatusOrdemServico antes = existente.getStatus();

        // Atualiza campos mutáveis
        mapper.updateEntityFromDTO(dto, existente);

        // Se trocou agendamento, atualiza relação
        if (dto.getAgendamentoId() != null &&
                !existente.getAgendamento().getIdAgendamento().equals(dto.getAgendamentoId())) {
            Agendamento novoAg = agendamentoRepo.findById(dto.getAgendamentoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Agendamento não encontrado para id " + dto.getAgendamentoId()));
            existente.setAgendamento(novoAg);
        }

        // Preenche dataRealizacao ao ASSINAR
        if (existente.getStatus() == StatusOrdemServico.ASSINADA
                && existente.getDataRealizacao() == null) {
            existente.setDataRealizacao(LocalDateTime.now());
        }

        OrdemServico atualizado = osRepo.save(existente);

        // Dispara eventos financeiros quando muda para ASSINADA/RECUSADA
        StatusOrdemServico depois = atualizado.getStatus();
        if (!antes.equals(depois) &&
                (depois == StatusOrdemServico.ASSINADA || depois == StatusOrdemServico.RECUSADA)) {

            eventPublisher.publishEvent(new OrdemServicoStatusChangedEvent(
                    atualizado.getId(),
                    atualizado.getStatus().name(),
                    atualizado.getValorServico(),
                    atualizado.getValorMateriais(),
                    atualizado.getKmDeslocamento()
            ));
        }

        return mapper.toDTO(atualizado);
    }

    @Override
    public void excluirOrdem(Long id) {
        OrdemServico os = osRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "OrdemServico não encontrada para id " + id));
        osRepo.delete(os);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Long> buscarIdPorAgendamento(Long agendamentoId) {
        return osRepo.findIdByAgendamentoId(agendamentoId);
    }
}
