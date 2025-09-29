package br.com.reparo360.financeiro.api;


import br.com.reparo360.financeiro.dto.ApuracaoServicoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Apurações de Serviço", description = "Consultas de apuração financeira de serviços")
@RequestMapping("/api/financeiro/apuracoes-servico")
public interface ApuracaoServicoApi {

    @Operation(summary = "Listar todas as apurações de serviço")
    @GetMapping
    ResponseEntity<List<ApuracaoServicoDTO>> listarTodas();

    @Operation(summary = "Buscar apuração de serviço por ID")
    @GetMapping("/{id}")
    ResponseEntity<ApuracaoServicoDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Listar apurações de serviço por Ordem de Serviço")
    @GetMapping("/ordem/{ordemServicoId}")
    ResponseEntity<List<ApuracaoServicoDTO>> listarPorOrdemServico(@PathVariable Long ordemServicoId);
}
