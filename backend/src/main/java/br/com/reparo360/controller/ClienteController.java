package br.com.reparo360.controller;

import br.com.reparo360.api.ClienteApi;
import br.com.reparo360.dto.ClienteDTO;
import br.com.reparo360.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteApi {

    private final ClienteService service;

    /**
     * Busca Cliente por e-mail.
     * GET /api/clientes/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteDTO> findByEmail(@PathVariable String email) {
        Optional<ClienteDTO> opt = service.findByEmail(email);
        return opt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os Clientes.
     * GET /api/clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca Cliente por ID.
     * GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        ClienteDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Upsert de Cliente baseado em e-mail:
     *   • Se já existir cliente com o DTO.email, atualiza nome e telefone (não mexe no endereço, pois
     *     o endereço fica dentro de Agendamento).
     *   • Senão, cria um novo Cliente.
     *
     * POST /api/clientes
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> createOrUpdateByEmail(
            @Valid @RequestBody ClienteDTO dto) {

        // A service.saveOrUpdate(dto) vai:
        // 1) procurar repo.findByEmailIgnoreCase(dto.getEmail())
        // 2) se encontrar, faz setNome, setTelefone no cliente existente e salva
        // 3) se não encontrar, constrói novo cliente, seta dataCadastro e salva
        //
        // Não alteramos endereço aqui, pois o endereço do cliente não é persistido em Cliente,
        // mas sim no Agendamento. Essa lógica já está em ClienteServiceImpl.

        ClienteDTO salvo = service.saveOrUpdate(dto);
        // Se o cliente foi criado agora, salva.getId() conterá o ID novo. Se foi apenas update, também retorna o DTO atualizado.
        return ResponseEntity.status(
                // Se salvou novo (id era nulo no DTO), devolve 201 CREATED; se id já existia, devolve 200 OK
                (dto.getIdCliente() == null) ? 201 : 200
        ).body(salvo);
    }

    /**
     * Atualiza um Cliente existente por ID (override total).
     * PUT /api/clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO dto) {

        // Garante que estamos realmente atualizando esse ID
        dto.setIdCliente(id);
        ClienteDTO atualizado = service.update(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove um Cliente por ID.
     * DELETE /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
