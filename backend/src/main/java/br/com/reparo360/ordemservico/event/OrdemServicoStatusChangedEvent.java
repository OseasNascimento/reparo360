package br.com.reparo360.ordemservico.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * Evento disparado sempre que o status de uma OS muda para ASSINADA ou RECUSADA.
 */
@Getter
@RequiredArgsConstructor
public class OrdemServicoStatusChangedEvent {
    private final Long osId;
    private final String status;            // "ASSINADA" ou "RECUSADA"
    private final BigDecimal valorServico;
    private final BigDecimal valorMateriais;
    private final Integer kmDeslocamento;
}
