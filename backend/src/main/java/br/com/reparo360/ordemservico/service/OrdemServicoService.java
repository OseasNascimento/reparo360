package br.com.reparo360.ordemservico.service;

import br.com.reparo360.ordemservico.dto.OrdemServicoDTO;
import br.com.reparo360.ordemservico.entity.StatusOrdemServico;

import java.util.List;
import java.util.Optional;

public interface OrdemServicoService {

    OrdemServicoDTO criarOrdem(Long agendamentoId);

    OrdemServicoDTO buscarPorId(Long id);

    List<OrdemServicoDTO> listarTodas();

    List<OrdemServicoDTO> listarPorStatus(StatusOrdemServico status);

    OrdemServicoDTO atualizarOrdem(Long id, OrdemServicoDTO dto);

    void excluirOrdem(Long id);

    // <<< NOVO: para o front consultar a OS por agendamento >>>
    Optional<Long> buscarIdPorAgendamento(Long agendamentoId);
}
