package br.com.reparo360.financeiro.controller;

import br.com.reparo360.financeiro.api.LancamentoFinanceiroApi;
import br.com.reparo360.financeiro.dto.LancamentoFinanceiroDTO;
import br.com.reparo360.financeiro.entity.TipoTransacao;
import br.com.reparo360.financeiro.service.LancamentoFinanceiroService;
import br.com.reparo360.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar Lançamentos Financeiros (CRUD + filtros).
 */
@RestController
@RequestMapping("/api/financeiro/lancamentos")
@RequiredArgsConstructor
public class LancamentoFinanceiroController implements LancamentoFinanceiroApi {

    private final LancamentoFinanceiroService lancamentoService;

    /**
     * POST /api/financeiro/lancamentos
     * Cria um novo lançamento financeiro.
     */
    @PostMapping
    public ResponseEntity<LancamentoFinanceiroDTO> criar(
            @Valid @RequestBody LancamentoFinanceiroDTO dto) {
        LancamentoFinanceiroDTO criado = lancamentoService.criarLancamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * GET /api/financeiro/lancamentos
     * Retorna todos os lançamentos financeiros.
     */
    @GetMapping
    public ResponseEntity<List<LancamentoFinanceiroDTO>> listarTodos() {
        List<LancamentoFinanceiroDTO> lista = lancamentoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/financeiro/lancamentos/{id}
     * Retorna um lançamento financeiro específico por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LancamentoFinanceiroDTO> buscarPorId(@PathVariable Long id) {
        LancamentoFinanceiroDTO dto = lancamentoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/financeiro/lancamentos/{id}
     * Atualiza um lançamento financeiro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LancamentoFinanceiroDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LancamentoFinanceiroDTO dto) {
        LancamentoFinanceiroDTO atualizado = lancamentoService.atualizarLancamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/financeiro/lancamentos/{id}
     * Exclui um lançamento financeiro por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        lancamentoService.excluirLancamento(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/financeiro/lancamentos/conta/{contaCaixaId}
     * Retorna todos os lançamentos de uma ContaCaixa específica.
     */
    @GetMapping("/conta/{contaCaixaId}")
    public ResponseEntity<List<LancamentoFinanceiroDTO>> listarPorConta(
            @PathVariable Long contaCaixaId) {
        List<LancamentoFinanceiroDTO> lista = lancamentoService.listarPorContaCaixa(contaCaixaId);
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/financeiro/lancamentos/tipo/{tipoTransacao}
     * Retorna todos os lançamentos filtrados pelo tipo de transação (RECEITA ou DESPESA).
     */
    @GetMapping("/tipo/{tipoTransacao}")
    public ResponseEntity<List<LancamentoFinanceiroDTO>> listarPorTipo(
            @PathVariable TipoTransacao tipoTransacao) {
        List<LancamentoFinanceiroDTO> lista = lancamentoService.listarPorTipo(tipoTransacao);
        return ResponseEntity.ok(lista);
    }
}
