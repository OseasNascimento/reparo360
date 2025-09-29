package br.com.reparo360.estoque.controller;

import br.com.reparo360.estoque.api.ProdutoApi;
import br.com.reparo360.estoque.dto.ProdutoDTO;
import br.com.reparo360.estoque.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de produtos em estoque.
 */
@RestController
@RequestMapping("/api/estoque/produtos")
@RequiredArgsConstructor
public class ProdutoController implements ProdutoApi {

    private final ProdutoService produtoService;

    /**
     * POST /api/estoque/produtos
     * Cria um novo produto.
     */
    @PostMapping
    public ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO criado = produtoService.criarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * GET /api/estoque/produtos
     * Lista todos os produtos.
     */
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        List<ProdutoDTO> lista = produtoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/estoque/produtos/{id}
     * Busca um produto por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO dto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/estoque/produtos/{id}
     * Atualiza um produto existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO atualizado = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/estoque/produtos/{id}
     * Exclui um produto por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }
}
