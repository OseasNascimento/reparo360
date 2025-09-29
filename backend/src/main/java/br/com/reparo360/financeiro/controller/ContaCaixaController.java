package br.com.reparo360.financeiro.controller;

import br.com.reparo360.financeiro.api.ContaCaixaApi;
import br.com.reparo360.financeiro.dto.ContaCaixaDTO;
import br.com.reparo360.financeiro.mapper.ContaCaixaMapper;
import br.com.reparo360.exception.ResourceNotFoundException;
import br.com.reparo360.financeiro.entity.ContaCaixa;
import br.com.reparo360.financeiro.repository.ContaCaixaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar contas caixa.
 */
@RestController
@RequestMapping("/api/financeiro/contas")
@RequiredArgsConstructor
public class ContaCaixaController implements ContaCaixaApi {

    private final ContaCaixaRepository contaRepo;
    private final ContaCaixaMapper contaMapper;

    @PostMapping
    public ResponseEntity<ContaCaixaDTO> criar(@Valid @RequestBody ContaCaixaDTO dto) {
        // Verifica existência de conta com mesmo nome
        if (contaRepo.findByNome(dto.getNome()).isPresent()) {
            throw new IllegalArgumentException("Conta com nome já existe: " + dto.getNome());
        }
        ContaCaixa entity = contaMapper.toEntity(dto);
        ContaCaixa salvo = contaRepo.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaMapper.toDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ContaCaixaDTO>> listarTodos() {
        return ResponseEntity.ok(
                contaMapper.toDTOList(contaRepo.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaCaixaDTO> buscarPorId(@PathVariable Long id) {
        ContaCaixa entity = contaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContaCaixa não encontrada com id " + id));
        return ResponseEntity.ok(contaMapper.toDTO(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaCaixaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ContaCaixaDTO dto) {
        ContaCaixa existente = contaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContaCaixa não encontrada com id " + id));
        contaMapper.updateEntityFromDTO(dto, existente);
        ContaCaixa atualizado = contaRepo.save(existente);
        return ResponseEntity.ok(contaMapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        ContaCaixa existente = contaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContaCaixa não encontrada com id " + id));
        contaRepo.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
