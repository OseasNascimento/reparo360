package br.com.reparo360.financeiro.service;

import br.com.reparo360.ordemservico.event.OrdemServicoStatusChangedEvent;

/**
 * Interface para gerenciar toda lógica financeira reativa a eventos de Ordem de Serviço.
 */
public interface GestaoFinanceiraService {

    /**
     * Registra as movimentações financeiras quando uma OS é ASSINADA:
     * - Gera lançamento de receita (para o valor do serviço)
     * - Gera lançamento de despesa (para valor de materiais e deslocamento)
     * - Gera registro de apuração financeira (Lucro/Receita líquida)
     */
    void registrarOSAssinada(OrdemServicoStatusChangedEvent event);

    /**
     * Registra apenas despesa (ou estorno) quando uma OS é RECUSADA:
     * - Pode ser um lançamento de despesa de devolução de materiais, etc.
     */
    void registrarOSRecusada(OrdemServicoStatusChangedEvent event);
}
