package br.com.reparo360.api;


import br.com.reparo360.dto.TecnicoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Técnicos", description = "Gerenciamento de técnicos")
@RequestMapping("/api/tecnicos")
public interface TecnicoApi {

    @Operation(summary = "Criar um novo técnico")
    @PostMapping
    ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO dto);

    @Operation(summary = "Listar todos os técnicos")
    @GetMapping
    ResponseEntity<List<TecnicoDTO>> findAll();

    @Operation(summary = "Buscar técnico por ID")
    @GetMapping("/{id}")
    ResponseEntity<TecnicoDTO> findById(@PathVariable Long id);

    @Operation(summary = "Atualizar um técnico existente")
    @PutMapping("/{id}")
    ResponseEntity<TecnicoDTO> update(@PathVariable Long id, @Valid @RequestBody TecnicoDTO dto);

    @Operation(summary = "Excluir um técnico por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
