package br.com.reparo360.ordemservico.entity;

/**
 * Enum para representar os possíveis status de uma Ordem de Serviço.
 */
public enum StatusOrdemServico {
    AGENDADA,
    EM_ANDAMENTO,
    ASSINADA,
    RECUSADA,
    CONCLUIDA,
    CANCELADA
}
