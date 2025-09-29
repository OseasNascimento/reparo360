package br.com.reparo360.financeiro.controller;

import br.com.reparo360.financeiro.api.CategoriaFinanceiraApi;
import br.com.reparo360.financeiro.dto.CategoriaFinanceiraDTO;
import br.com.reparo360.financeiro.service.CategoriaFinanceiraService;
import br.com.reparo360.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar categorias financeiras (CRUD completo).
 */
@RestController
@RequestMapping("/api/financeiro/categorias")
@RequiredArgsConstructor
public class CategoriaFinanceiraController implements CategoriaFinanceiraApi {

    private final CategoriaFinanceiraService categoriaService;

    /**
     * POST /api/financeiro/categorias
     * Cria uma nova categoria financeira.
     */
    @PostMapping
    public ResponseEntity<CategoriaFinanceiraDTO> criar(
            @Valid @RequestBody CategoriaFinanceiraDTO dto) {
        CategoriaFinanceiraDTO criado = categoriaService.criarCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * GET /api/financeiro/categorias
     * Retorna todas as categorias financeiras.
     */
    @GetMapping
    public ResponseEntity<List<CategoriaFinanceiraDTO>> listarTodas() {
        List<CategoriaFinanceiraDTO> lista = categoriaService.listarTodas();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/financeiro/categorias/{id}
     * Retorna uma categoria por seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaFinanceiraDTO> buscarPorId(@PathVariable Long id) {
        CategoriaFinanceiraDTO dto = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/financeiro/categorias/{id}
     * Atualiza uma categoria existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaFinanceiraDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaFinanceiraDTO dto) {
        CategoriaFinanceiraDTO atualizado = categoriaService.atualizarCategoria(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/financeiro/categorias/{id}
     * Exclui uma categoria por seu ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaService.excluirCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
