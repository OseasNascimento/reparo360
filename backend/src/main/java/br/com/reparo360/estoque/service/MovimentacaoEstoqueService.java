package br.com.reparo360.estoque.service;

import br.com.reparo360.estoque.dto.MovimentacaoEstoqueDTO;
import java.util.List;

/**
 * Interface de serviço para Movimentação de Estoque.
 */
public interface MovimentacaoEstoqueService {

    /**
     * Registra uma movimentação (entrada ou saída) para o estoque.
     */
    MovimentacaoEstoqueDTO registrarMovimentacao(MovimentacaoEstoqueDTO dto);

    /**
     * Lista todas as movimentações de um produto específico.
     */
    List<MovimentacaoEstoqueDTO> listarPorProduto(Long produtoId);

    /**
     * Lista todas as movimentações de estoque (sem filtro).
     */
    List<MovimentacaoEstoqueDTO> listarTodas();
}
