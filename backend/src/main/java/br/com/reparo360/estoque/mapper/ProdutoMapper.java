package br.com.reparo360.estoque.mapper;

import br.com.reparo360.estoque.dto.ProdutoDTO;
import br.com.reparo360.estoque.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toEntity(ProdutoDTO dto);

    ProdutoDTO toDTO(Produto entity);

    List<ProdutoDTO> toDTOList(List<Produto> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ProdutoDTO dto, @MappingTarget Produto entity);
}
