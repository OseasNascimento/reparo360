package br.com.reparo360.service.impl;

import br.com.reparo360.dto.RoleDTO;
import br.com.reparo360.mapper.RoleMapper;
import br.com.reparo360.entity.Role;
import br.com.reparo360.repository.RoleRepository;
import br.com.reparo360.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repo;
    private final RoleMapper mapper;

    @Override
    @Transactional
    public RoleDTO create(RoleDTO dto) {
        Role salvo = repo.save(mapper.toEntity(dto));
        return mapper.toDTO(salvo);
    }

    @Override
    public List<RoleDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public RoleDTO findById(Long id) {
        Role r = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));
        return mapper.toDTO(r);
    }

    @Override
    @Transactional
    public RoleDTO update(Long id, RoleDTO dto) {
        repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));
        Role atualizado = mapper.toEntity(dto);
        atualizado.setIdRole(id);
        repo.save(atualizado);
        return mapper.toDTO(atualizado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
