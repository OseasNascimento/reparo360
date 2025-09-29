package br.com.reparo360.estoque.api;


import br.com.reparo360.estoque.dto.MovimentacaoEstoqueDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Movimentações de Estoque", description = "Gerenciamento de entradas e saídas de estoque")
@RequestMapping("/api/estoque/movimentacoes")
public interface MovimentacaoEstoqueApi {

    @Operation(summary = "Registrar uma movimentação de estoque (entrada ou saída)")
    @PostMapping
    ResponseEntity<MovimentacaoEstoqueDTO> registrar(@Valid @RequestBody MovimentacaoEstoqueDTO dto);

    @Operation(summary = "Listar todas as movimentações de um produto específico")
    @GetMapping("/produto/{produtoId}")
    ResponseEntity<List<MovimentacaoEstoqueDTO>> listarPorProduto(@PathVariable Long produtoId);

    @Operation(summary = "Listar todas as movimentações de estoque")
    @GetMapping
    ResponseEntity<List<MovimentacaoEstoqueDTO>> listarTodas();
}
