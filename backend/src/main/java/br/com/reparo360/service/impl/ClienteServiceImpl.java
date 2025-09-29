package br.com.reparo360.service.impl;

import br.com.reparo360.dto.ClienteDTO;
import br.com.reparo360.mapper.ClienteMapper;
import br.com.reparo360.entity.Cliente;
import br.com.reparo360.repository.ClienteRepository;
import br.com.reparo360.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;
    private final ClienteMapper mapper;

    @Override
    @Transactional
    public ClienteDTO create(ClienteDTO dto) {
        Cliente entity = mapper.toEntity(dto);
        entity.setDataCadastro(LocalDateTime.now());
        Cliente salvo = repo.save(entity);
        return mapper.toDto(salvo);
    }

    @Override
    @Transactional
    public List<ClienteDTO> findAll() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    @Transactional
    public ClienteDTO findById(Long id) {
        Cliente cliente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
        return mapper.toDto(cliente);
    }

    @Override
    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente existente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado para atualizar: " + id));
        mapper.updateEntityFromDto(dto, existente);
        Cliente atualizado = repo.save(existente);
        return mapper.toDto(atualizado);
    }

    @Override
    public Optional<ClienteDTO> findByEmail(String email) {
        return repo.findByEmailIgnoreCase(email)
                .map(ent -> mapper.toDto(ent));
    }

    @Override
    @Transactional
    public ClienteDTO saveOrUpdate(ClienteDTO dto) {
        // 1. Tenta achar cliente existente
        Optional<Cliente> opt = repo.findByEmailIgnoreCase(dto.getEmail());

        Cliente entidade;
        if (opt.isPresent()) {
            // Já existe → atualiza campos mutáveis
            entidade = opt.get();
            entidade.setNome(dto.getNome());
            entidade.setTelefone(dto.getTelefone());
            // NÃO sobrescreva o campo 'endereco' do Cliente,
            // pois estamos armazenando endereço dentro do Agendamento
        } else {
            // Não existe → cria um novo
            entidade = mapper.toEntity(dto);
            // Seta data de cadastro agora (LocalDateTime) se mapper não fizer
            entidade.setDataCadastro(LocalDateTime.now());
        }
        Cliente gravado = repo.save(entidade);
        return mapper.toDto(gravado);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado para remover: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public Cliente findEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
    }
}

