package br.com.reparo360.controller;

import br.com.reparo360.api.AgendamentoApi;
import br.com.reparo360.dto.AgendamentoDTO;
import br.com.reparo360.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController implements AgendamentoApi {

    private final AgendamentoService service;

    @PostMapping
    public ResponseEntity<AgendamentoDTO> create(@Valid @RequestBody AgendamentoDTO dto) {
        AgendamentoDTO criado = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Se seu update geral realmente só atualiza status, ok — senão chame service.update(id, dto). */
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> update(@PathVariable Long id, @Valid @RequestBody AgendamentoDTO dto) {
        AgendamentoDTO atualizado = service.updateStatus(id, dto.getStatus());
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AgendamentoDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        AgendamentoDTO atualizado = service.updateStatus(id, status);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<LocalDateTime>> ocupados(
            @RequestParam Long tecnicoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia
    ) {
        List<LocalDateTime> slots = service.findOccupiedSlots(tecnicoId, dia);
        return ResponseEntity.ok(slots);
    }
}
