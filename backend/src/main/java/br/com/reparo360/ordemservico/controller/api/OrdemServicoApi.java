package br.com.reparo360.ordemservico.controller.api;


import br.com.reparo360.ordemservico.dto.OrdemServicoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ordens de Serviço", description = "Gerenciamento de Ordens de Serviço")
@RequestMapping("/api/os")
public interface OrdemServicoApi {

    @Operation(summary = "Criar uma nova Ordem de Serviço a partir de um agendamento")
    @PostMapping
    ResponseEntity<OrdemServicoDTO> criar(@Valid @RequestBody OrdemServicoDTO dto);

    @Operation(summary = "Listar todas as Ordens de Serviço")
    @GetMapping
    ResponseEntity<List<OrdemServicoDTO>> listarTodas();

    @Operation(summary = "Buscar uma Ordem de Serviço por ID")
    @GetMapping("/{id}")
    ResponseEntity<OrdemServicoDTO> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar uma Ordem de Serviço existente")
    @PutMapping("/{id}")
    ResponseEntity<OrdemServicoDTO> atualizar(@PathVariable Long id,
                                              @Valid @RequestBody OrdemServicoDTO dto);

    @Operation(summary = "Excluir uma Ordem de Serviço por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id);
}
