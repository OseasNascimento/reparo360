package br.com.reparo360.ordemservico.controller;

import br.com.reparo360.ordemservico.dto.OrdemServicoDTO;
import br.com.reparo360.ordemservico.entity.StatusOrdemServico;
import br.com.reparo360.ordemservico.service.OrdemServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrdemServicoController {

    private final OrdemServicoService osService;

    // Cria (ou retorna existente) a OS para um agendamento
    @PostMapping("/agendamentos/{agendamentoId}/os")
    public ResponseEntity<OrdemServicoDTO> criarOrdem(@PathVariable Long agendamentoId) {
        OrdemServicoDTO criado = osService.criarOrdem(agendamentoId);
        return ResponseEntity.status(201).body(criado);
    }

    // >>> NOVO: obter o ID da OS a partir do agendamento (front consulta antes de criar)
    @GetMapping("/os/by-agendamento/{agendamentoId}")
    public ResponseEntity<?> buscarPorAgendamento(@PathVariable Long agendamentoId) {
        return osService.buscarIdPorAgendamento(agendamentoId)
                .<ResponseEntity<?>>map(id -> ResponseEntity.ok(Map.of("id", id)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/os")
    public ResponseEntity<List<OrdemServicoDTO>> listarTodas() {
        return ResponseEntity.ok(osService.listarTodas());
    }

    @GetMapping("/os/{id}")
    public ResponseEntity<OrdemServicoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(osService.buscarPorId(id));
    }

    @GetMapping("/os/status")
    public ResponseEntity<List<OrdemServicoDTO>> listarPorStatus(@RequestParam StatusOrdemServico status) {
        return ResponseEntity.ok(osService.listarPorStatus(status));
    }

    @PutMapping("/os/{id}")
    public ResponseEntity<OrdemServicoDTO> atualizarOrdem(@PathVariable Long id,
                                                          @RequestBody OrdemServicoDTO dto) {
        return ResponseEntity.ok(osService.atualizarOrdem(id, dto));
    }

    @DeleteMapping("/os/{id}")
    public ResponseEntity<Void> excluirOrdem(@PathVariable Long id) {
        osService.excluirOrdem(id);
        return ResponseEntity.noContent().build();
    }
}
