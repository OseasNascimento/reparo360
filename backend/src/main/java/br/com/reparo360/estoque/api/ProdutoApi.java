package br.com.reparo360.estoque.api;


import br.com.reparo360.estoque.dto.ProdutoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produtos", description = "Gerenciamento de produtos em estoque")
@RequestMapping("/api/estoque/produtos")
public interface ProdutoApi {

    @Operation(summary = "Criar um novo produto")
    @PostMapping
    ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoDTO dto);

    @Operation(summary = "Listar todos os produtos")
    @GetMapping
    ResponseEntity<List<ProdutoDTO>> listarTodos();

    @Operation(summary = "Buscar produto por ID")
    @GetMapping("/{id}")
    ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar um produto existente")
    @PutMapping("/{id}")
    ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto);

    @Operation(summary = "Excluir um produto por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
