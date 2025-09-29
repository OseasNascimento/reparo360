package br.com.reparo360.mapper;

import br.com.reparo360.dto.ServicoDTO;
import br.com.reparo360.entity.Servico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicoMapper {

    @Mapping(target = "idServico", ignore = true)
    Servico toEntity(ServicoDTO dto);


    ServicoDTO toDTO(Servico entity);

    @Mapping(target = "idServico", ignore = true)
    void updateEntityFromDto(ServicoDTO dto, @MappingTarget Servico entity);

    List<ServicoDTO> toDTOList(List<Servico> entities);
}
