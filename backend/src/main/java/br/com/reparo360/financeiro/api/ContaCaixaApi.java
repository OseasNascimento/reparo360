package br.com.reparo360.financeiro.api;



import br.com.reparo360.financeiro.dto.ContaCaixaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contas Caixa", description = "Gerenciamento de contas caixa")
@RequestMapping("/api/financeiro/contas")
public interface ContaCaixaApi {

    @Operation(summary = "Criar uma nova conta caixa")
    @PostMapping
    ResponseEntity<ContaCaixaDTO> criar(@Valid @RequestBody ContaCaixaDTO dto);

    @Operation(summary = "Listar todas as contas caixa")
    @GetMapping
    ResponseEntity<List<ContaCaixaDTO>> listarTodos();

    @Operation(summary = "Buscar conta caixa por ID")
    @GetMapping("/{id}")
    ResponseEntity<ContaCaixaDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar uma conta caixa existente")
    @PutMapping("/{id}")
    ResponseEntity<ContaCaixaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ContaCaixaDTO dto);

    @Operation(summary = "Excluir uma conta caixa por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
