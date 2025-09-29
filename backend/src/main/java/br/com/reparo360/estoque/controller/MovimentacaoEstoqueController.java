package br.com.reparo360.estoque.controller;

import br.com.reparo360.estoque.api.MovimentacaoEstoqueApi;
import br.com.reparo360.estoque.dto.MovimentacaoEstoqueDTO;
import br.com.reparo360.estoque.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de Movimentações de Estoque.
 */
@RestController
@RequestMapping("/api/estoque/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoEstoqueController implements MovimentacaoEstoqueApi {

    private final MovimentacaoEstoqueService movService;

    /**
     * POST /api/estoque/movimentacoes
     * Registra uma movimentação de estoque (entrada ou saída).
     */
    @PostMapping
    public ResponseEntity<MovimentacaoEstoqueDTO> registrar(@Valid @RequestBody MovimentacaoEstoqueDTO dto) {
        MovimentacaoEstoqueDTO salva = movService.registrarMovimentacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    /**
     * GET /api/estoque/movimentacoes/produto/{produtoId}
     * Lista todas as movimentações de um produto específico.
     */
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<MovimentacaoEstoqueDTO>> listarPorProduto(@PathVariable Long produtoId) {
        List<MovimentacaoEstoqueDTO> lista = movService.listarPorProduto(produtoId);
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/estoque/movimentacoes
     * Lista todas as movimentações de estoque.
     */
    @GetMapping
    public ResponseEntity<List<MovimentacaoEstoqueDTO>> listarTodas() {
        return ResponseEntity.ok(movService.listarTodas());
    }
}
