package br.com.reparo360.financeiro.api;



import br.com.reparo360.financeiro.dto.CategoriaFinanceiraDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categorias Financeiras", description = "Gerenciamento de categorias financeiras")
@RequestMapping("/api/financeiro/categorias")
public interface CategoriaFinanceiraApi {

    @Operation(summary = "Criar uma nova categoria financeira")
    @PostMapping
    ResponseEntity<CategoriaFinanceiraDTO> criar(@Valid @RequestBody CategoriaFinanceiraDTO dto);

    @Operation(summary = "Listar todas as categorias financeiras")
    @GetMapping
    ResponseEntity<List<CategoriaFinanceiraDTO>> listarTodas();

    @Operation(summary = "Buscar categoria financeira por ID")
    @GetMapping("/{id}")
    ResponseEntity<CategoriaFinanceiraDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar uma categoria financeira existente")
    @PutMapping("/{id}")
    ResponseEntity<CategoriaFinanceiraDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaFinanceiraDTO dto);

    @Operation(summary = "Excluir categoria financeira por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
