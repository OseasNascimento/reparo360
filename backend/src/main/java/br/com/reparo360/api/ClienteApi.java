package br.com.reparo360.api;

import br.com.reparo360.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Gerenciamento de clientes")
@RequestMapping("/api/clientes")
public interface ClienteApi {

    @Operation(summary = "Buscar cliente por e-mail")
    @GetMapping("/email/{email}")
    ResponseEntity<ClienteDTO> findByEmail(@PathVariable String email);

    @Operation(summary = "Listar todos os clientes")
    @GetMapping
    ResponseEntity<List<ClienteDTO>> findAll();

    @Operation(summary = "Buscar cliente por ID")
    @GetMapping("/{id}")
    ResponseEntity<ClienteDTO> findById(@PathVariable Long id);

    @Operation(summary = "Criar ou atualizar cliente pelo e-mail")
    @PostMapping
    ResponseEntity<ClienteDTO> createOrUpdateByEmail(@Valid @RequestBody ClienteDTO dto);

    @Operation(summary = "Atualizar plenamente um cliente existente")
    @PutMapping("/{id}")
    ResponseEntity<ClienteDTO> update(@PathVariable Long id,
                                      @Valid @RequestBody ClienteDTO dto);

    @Operation(summary = "Remover cliente por ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
