package br.com.reparo360.estoque.service;

import br.com.reparo360.estoque.dto.PecaUtilizadaDTO;
import java.util.List;

/**
 * Interface de serviço para peças utilizadas em Ordem de Serviço.
 */
public interface PecaUtilizadaService {

    /**
     * Salva todas as peças utilizadas vinculadas a uma Ordem de Serviço.
     * O serviço de OS passará o orderServicoId e lista de DTOs.
     */
    List<PecaUtilizadaDTO> salvarTodas(Long ordemServicoId, List<PecaUtilizadaDTO> dtos);

    /**
     * Lista todas as peças utilizadas por uma determinada OS.
     */
    List<PecaUtilizadaDTO> listarPorOrdemServico(Long ordemServicoId);
}
