package br.com.reparo360.estoque.api;

import br.com.reparo360.estoque.dto.PecaUtilizadaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Peças Utilizadas", description = "Gerenciamento de peças utilizadas em Ordens de Serviço")
@RequestMapping("/api/estoque/pecas-utilizadas")
public interface PecaUtilizadaApi {

    @Operation(summary = "Salvar peças utilizadas para uma ordem de serviço")
    @PostMapping("/ordem/{ordemId}")
    ResponseEntity<List<PecaUtilizadaDTO>> salvarPorOrdem(
            @PathVariable("ordemId") Long ordemId,
            @Valid @RequestBody List<PecaUtilizadaDTO> dtos);

    @Operation(summary = "Listar peças utilizadas de uma ordem de serviço específica")
    @GetMapping("/ordem/{ordemId}")
    ResponseEntity<List<PecaUtilizadaDTO>> listarPorOrdem(@PathVariable("ordemId") Long ordemId);
}
