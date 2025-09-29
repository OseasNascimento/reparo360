package br.com.reparo360.financeiro.service;

import br.com.reparo360.financeiro.dto.LancamentoFinanceiroDTO;
import br.com.reparo360.financeiro.entity.TipoTransacao;

import java.util.List;

/**
 * Interface de serviço para LancamentoFinanceiro (CRUD + consultas por conta e por tipo).
 */
public interface LancamentoFinanceiroService {

    /**
     * Cria um novo lançamento financeiro.
     */
    LancamentoFinanceiroDTO criarLancamento(LancamentoFinanceiroDTO dto);

    /**
     * Atualiza um lançamento existente, por ID.
     */
    LancamentoFinanceiroDTO atualizarLancamento(Long id, LancamentoFinanceiroDTO dto);

    /**
     * Busca um lançamento financeiro por ID.
     */
    LancamentoFinanceiroDTO buscarPorId(Long id);

    /**
     * Lista todos os lançamentos financeiros.
     */
    List<LancamentoFinanceiroDTO> listarTodos();

    /**
     * Exclui um lançamento por ID.
     */
    void excluirLancamento(Long id);

    /**
     * Lista lançamentos de uma determinada conta, por contaCaixaId.
     */
    List<LancamentoFinanceiroDTO> listarPorContaCaixa(Long contaCaixaId);

    /**
     * Lista lançamentos de acordo com o tipo de transação (RECEITA ou DESPESA).
     */
    List<LancamentoFinanceiroDTO> listarPorTipo(TipoTransacao tipoTransacao);
}
