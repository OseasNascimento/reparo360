package br.com.reparo360.financeiro.service;

import br.com.reparo360.financeiro.dto.ApuracaoServicoDTO;

import java.util.List;

/**
 * Interface de serviço para ApuracaoServico (operações de leitura).
 */
public interface ApuracaoServicoService {

    /**
     * Lista todas as apurações de serviço.
     */
    List<ApuracaoServicoDTO> listarTodas();

    /**
     * Busca uma apuração por ID.
     */
    ApuracaoServicoDTO buscarPorId(Long id);

    /**
     * Lista todas as apurações vinculadas a uma determinada Ordem de Serviço.
     */
    List<ApuracaoServicoDTO> listarPorOrdemServico(Long ordemServicoId);
}
