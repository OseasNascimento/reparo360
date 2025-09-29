package br.com.reparo360.estoque.controller;

import br.com.reparo360.estoque.api.PecaUtilizadaApi;
import br.com.reparo360.estoque.dto.PecaUtilizadaDTO;
import br.com.reparo360.estoque.service.PecaUtilizadaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de peças utilizadas em uma Ordem de Serviço.
 */
@RestController
@RequestMapping("/api/estoque/pecas-utilizadas")
@RequiredArgsConstructor
public class PecaUtilizadaController implements PecaUtilizadaApi {

    private final PecaUtilizadaService service;

    /**
     * POST /api/estoque/pecas-utilizadas/ordem/{ordemId}
     * Salva todas as peças utilizadas vinculadas a uma Ordem de Serviço.
     */
    @PostMapping("/ordem/{ordemId}")
    public ResponseEntity<List<PecaUtilizadaDTO>> salvarPorOrdem(
            @PathVariable("ordemId") Long ordemId,
            @Valid @RequestBody List<PecaUtilizadaDTO> dtos) {
        List<PecaUtilizadaDTO> salvas = service.salvarTodas(ordemId, dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvas);
    }

    /**
     * GET /api/estoque/pecas-utilizadas/ordem/{ordemId}
     * Lista todas as peças utilizadas em determinada Ordem de Serviço.
     */
    @GetMapping("/ordem/{ordemId}")
    public ResponseEntity<List<PecaUtilizadaDTO>> listarPorOrdem(@PathVariable("ordemId") Long ordemId) {
        List<PecaUtilizadaDTO> lista = service.listarPorOrdemServico(ordemId);
        return ResponseEntity.ok(lista);
    }
}
