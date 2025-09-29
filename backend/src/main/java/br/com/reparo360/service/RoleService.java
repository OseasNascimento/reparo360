package br.com.reparo360.service;

import br.com.reparo360.dto.RoleDTO;
import java.util.List;

public interface RoleService {
    RoleDTO create(RoleDTO dto);
    List<RoleDTO> findAll();
    RoleDTO findById(Long id);
    RoleDTO update(Long id, RoleDTO dto);
    void delete(Long id);
}