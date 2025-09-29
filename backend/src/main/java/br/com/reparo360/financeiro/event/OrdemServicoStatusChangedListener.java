package br.com.reparo360.financeiro.event;

import br.com.reparo360.financeiro.service.GestaoFinanceiraService;
import br.com.reparo360.ordemservico.event.OrdemServicoStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener que captura eventos de mudança de status de OS
 * e dispara a lógica financeira correspondente.
 */
@Component
@RequiredArgsConstructor
public class OrdemServicoStatusChangedListener {

    private final GestaoFinanceiraService gestao;

    @EventListener
    public void onStatusChanged(OrdemServicoStatusChangedEvent evt) {
        String status = evt.getStatus();
        if ("ASSINADA".equalsIgnoreCase(status)) {
            gestao.registrarOSAssinada(evt);
        } else if ("RECUSADA".equalsIgnoreCase(status)) {
            gestao.registrarOSRecusada(evt);
        }
        // Se houver outros status (e.g., CANCELADA), pode ignorar ou adicionar lógica.
    }
}
