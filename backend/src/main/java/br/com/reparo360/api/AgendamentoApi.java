package br.com.reparo360.api;

import br.com.reparo360.dto.AgendamentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Agendamentos", description = "Gerenciamento de agendamentos de serviços")
@RequestMapping("/api/agendamentos")
public interface AgendamentoApi {

    @Operation(summary = "Criar um novo agendamento")
    @PostMapping
    ResponseEntity<AgendamentoDTO> create(@Valid @RequestBody AgendamentoDTO dto);

    @Operation(summary = "Listar todos os agendamentos")
    @GetMapping
    ResponseEntity<List<AgendamentoDTO>> findAll();

    @Operation(summary = "Buscar agendamento por ID")
    @GetMapping("/{id}")
    ResponseEntity<AgendamentoDTO> findById(@PathVariable Long id);

    @Operation(summary = "Atualizar status do agendamento")
    @PutMapping("/{id}")
    ResponseEntity<AgendamentoDTO> update(@PathVariable Long id,
                                          @Valid @RequestBody AgendamentoDTO dto);

    @Operation(summary = "Alterar status do agendamento via PATCH")
    @PatchMapping("/{id}/status")
    ResponseEntity<AgendamentoDTO> updateStatus(@PathVariable Long id,
                                                @RequestParam(name = "status") @NotNull String status);

    @Operation(summary = "Excluir agendamento por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
