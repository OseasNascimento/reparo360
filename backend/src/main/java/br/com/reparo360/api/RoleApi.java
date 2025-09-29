package br.com.reparo360.api;

import br.com.reparo360.dto.RoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Roles", description = "Gerenciamento de papéis (roles)")
@RequestMapping("/api/roles")
public interface RoleApi {

    @Operation(summary = "Criar um novo role")
    @PostMapping
    ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO dto);

    @Operation(summary = "Listar todos os roles")
    @GetMapping
    ResponseEntity<List<RoleDTO>> findAll();

    @Operation(summary = "Buscar role por ID")
    @GetMapping("/{id}")
    ResponseEntity<RoleDTO> findById(@PathVariable Long id);

    @Operation(summary = "Atualizar um role existente")
    @PutMapping("/{id}")
    ResponseEntity<RoleDTO> update(@PathVariable Long id, @Valid @RequestBody RoleDTO dto);

    @Operation(summary = "Excluir um role por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
