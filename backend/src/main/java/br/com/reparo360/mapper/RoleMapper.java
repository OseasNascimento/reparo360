// RoleMapper.java
package br.com.reparo360.mapper;

import br.com.reparo360.dto.RoleDTO;
import br.com.reparo360.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDTO(Role entity);
    Role toEntity(RoleDTO dto);
    List<RoleDTO> toDTOList(List<Role> entities);
    List<RoleDTO> toDTOList(Set<Role> entities);
}
