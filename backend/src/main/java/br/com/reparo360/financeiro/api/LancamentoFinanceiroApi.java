package br.com.reparo360.financeiro.api;


import br.com.reparo360.financeiro.dto.LancamentoFinanceiroDTO;
import br.com.reparo360.financeiro.entity.TipoTransacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lançamentos Financeiros", description = "Gerenciamento de lançamentos financeiros")
@RequestMapping("/api/financeiro/lancamentos")
public interface LancamentoFinanceiroApi {

    @Operation(summary = "Criar um novo lançamento financeiro")
    @PostMapping
    ResponseEntity<LancamentoFinanceiroDTO> criar(@Valid @RequestBody LancamentoFinanceiroDTO dto);

    @Operation(summary = "Listar todos os lançamentos financeiros")
    @GetMapping
    ResponseEntity<List<LancamentoFinanceiroDTO>> listarTodos();

    @Operation(summary = "Buscar lançamento financeiro por ID")
    @GetMapping("/{id}")
    ResponseEntity<LancamentoFinanceiroDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar um lançamento financeiro existente")
    @PutMapping("/{id}")
    ResponseEntity<LancamentoFinanceiroDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LancamentoFinanceiroDTO dto);

    @Operation(summary = "Excluir um lançamento financeiro por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);

    @Operation(summary = "Listar lançamentos de uma conta caixa específica")
    @GetMapping("/conta/{contaCaixaId}")
    ResponseEntity<List<LancamentoFinanceiroDTO>> listarPorConta(@PathVariable Long contaCaixaId);

    @Operation(summary = "Listar lançamentos filtrados por tipo de transação")
    @GetMapping("/tipo/{tipoTransacao}")
    ResponseEntity<List<LancamentoFinanceiroDTO>> listarPorTipo(@PathVariable TipoTransacao tipoTransacao);
}
