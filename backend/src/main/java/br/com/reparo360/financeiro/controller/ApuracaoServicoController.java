package br.com.reparo360.financeiro.controller;

import br.com.reparo360.financeiro.api.ApuracaoServicoApi;
import br.com.reparo360.financeiro.dto.ApuracaoServicoDTO;
import br.com.reparo360.financeiro.service.ApuracaoServicoService;
import br.com.reparo360.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para consultar Apuração de Serviço.
 * Somente leitura: listar todas, buscar por id e listar por ordem de serviço.
 */
@RestController
@RequestMapping("/api/financeiro/apuracoes-servico")
@RequiredArgsConstructor
public class ApuracaoServicoController implements ApuracaoServicoApi {

    private final ApuracaoServicoService apuracaoService;

    /**
     * GET /api/financeiro/apuracoes-servico
     * Retorna todas as apurações.
     */
    @GetMapping
    public ResponseEntity<List<ApuracaoServicoDTO>> listarTodas() {
        List<ApuracaoServicoDTO> lista = apuracaoService.listarTodas();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/financeiro/apuracoes-servico/{id}
     * Retorna uma apuração por seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApuracaoServicoDTO> buscarPorId(@PathVariable Long id) {
        ApuracaoServicoDTO dto = apuracaoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * GET /api/financeiro/apuracoes-servico/ordem/{ordemServicoId}
     * Retorna todas as apurações associadas a uma Ordem de Serviço específica.
     */
    @GetMapping("/ordem/{ordemServicoId}")
    public ResponseEntity<List<ApuracaoServicoDTO>> listarPorOrdemServico(
            @PathVariable Long ordemServicoId) {
        List<ApuracaoServicoDTO> lista = apuracaoService.listarPorOrdemServico(ordemServicoId);
        return ResponseEntity.ok(lista);
    }
}
