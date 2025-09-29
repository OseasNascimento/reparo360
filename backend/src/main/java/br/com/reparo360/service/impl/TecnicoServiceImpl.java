package br.com.reparo360.service.impl;

import br.com.reparo360.dto.TecnicoDTO;
import br.com.reparo360.exception.DuplicateEmailException;
import br.com.reparo360.exception.TecnicoNotFoundException;
import br.com.reparo360.mapper.TecnicoMapper;
import br.com.reparo360.entity.Role;
import br.com.reparo360.entity.Tecnico;
import br.com.reparo360.repository.RoleRepository;
import br.com.reparo360.repository.TecnicoRepository;
import br.com.reparo360.service.TecnicoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TecnicoServiceImpl implements TecnicoService {

    private final TecnicoRepository repo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final TecnicoMapper mapper;

    @Override
    @Transactional
    public TecnicoDTO create(TecnicoDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("E-mail já cadastrado: " + dto.getEmail());
        }
        Tecnico entity = mapper.toEntity(dto);
        entity.setSenha(passwordEncoder.encode(dto.getSenha()));
        Role role = roleRepo.findByNomeRole("ROLE_TECNICO")
                .orElseThrow(() -> new TecnicoNotFoundException("Role ROLE_TECNICO não encontrada"));
        entity.setRoles(Set.of(role));
        Tecnico salvo = repo.save(entity);
        return mapper.toDTO(salvo);
    }

    @Override
    public List<TecnicoDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public TecnicoDTO findById(Long id) {
        Tecnico t = repo.findById(id)
                .orElseThrow(() -> new TecnicoNotFoundException("Técnico não encontrado com id: " + id));
        return mapper.toDTO(t);
    }

    @Override
    @Transactional
    public TecnicoDTO update(Long id, TecnicoDTO dto) {
        if (!repo.existsById(id)) {
            throw new TecnicoNotFoundException("Técnico não encontrado com id: " + id);
        }
        // Se o e-mail mudou, checar duplicidade
        if (!repo.findById(id).get().getEmail().equals(dto.getEmail())
                && repo.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("E-mail já cadastrado: " + dto.getEmail());
        }
        Tecnico atualizado = mapper.toEntity(dto);
        atualizado.setId(id);
        atualizado.setSenha(passwordEncoder.encode(dto.getSenha()));
        tecnicoSalvarRoles(atualizado, dto);
        repo.save(atualizado);
        return mapper.toDTO(atualizado);
    }

    private void tecnicoSalvarRoles(Tecnico entity, TecnicoDTO dto) {
        // mantêm roles originais ou reatribui ROLE_TECNICO se vazio
        if (entity.getRoles() == null || entity.getRoles().isEmpty()) {
            Role role = roleRepo.findByNomeRole("ROLE_TECNICO")
                    .orElseThrow(() -> new TecnicoNotFoundException("Role ROLE_TECNICO não encontrada"));
            entity.setRoles(Set.of(role));
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new TecnicoNotFoundException("Técnico não encontrado com id: " + id);
        }
        repo.deleteById(id);
    }
}
