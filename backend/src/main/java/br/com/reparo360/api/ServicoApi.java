package br.com.reparo360.api;


import br.com.reparo360.dto.ServicoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Serviços", description = "Gerenciamento de serviços oferecidos")
@RequestMapping("/api/servicos")
public interface ServicoApi {

    @Operation(summary = "Criar um novo serviço")
    @PostMapping
    ResponseEntity<ServicoDTO> create(@Valid @RequestBody ServicoDTO dto);

    @Operation(summary = "Listar todos os serviços")
    @GetMapping
    ResponseEntity<List<ServicoDTO>> findAll();

    @Operation(summary = "Buscar serviço por ID")
    @GetMapping("/{id}")
    ResponseEntity<ServicoDTO> findById(@PathVariable Long id);

    @Operation(summary = "Atualizar um serviço existente")
    @PutMapping("/{id}")
    ResponseEntity<ServicoDTO> update(@PathVariable Long id,
                                      @Valid @RequestBody ServicoDTO dto);

    @Operation(summary = "Excluir um serviço por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
