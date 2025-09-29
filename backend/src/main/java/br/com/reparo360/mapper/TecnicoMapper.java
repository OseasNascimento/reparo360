// TecnicoMapper.java
package br.com.reparo360.mapper;

import br.com.reparo360.dto.TecnicoDTO;
import br.com.reparo360.entity.Tecnico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface TecnicoMapper {

    @Mapping(source = "roles", target = "roles")
    TecnicoDTO toDTO(Tecnico entity);

    @Mapping(target = "roles", ignore = true)
    Tecnico toEntity(TecnicoDTO dto);

    List<TecnicoDTO> toDTOList(Set<Tecnico> entities);
}
