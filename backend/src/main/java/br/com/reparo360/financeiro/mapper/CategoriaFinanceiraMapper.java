package br.com.reparo360.financeiro.mapper;

import br.com.reparo360.financeiro.dto.CategoriaFinanceiraDTO;
import br.com.reparo360.financeiro.entity.CategoriaFinanceira;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper para CategoriaFinanceira ↔ CategoriaFinanceiraDTO.
 */
@Mapper(componentModel = "spring")
public interface CategoriaFinanceiraMapper {

    CategoriaFinanceira toEntity(CategoriaFinanceiraDTO dto);

    CategoriaFinanceiraDTO toDTO(CategoriaFinanceira entity);

    List<CategoriaFinanceiraDTO> toDTOList(List<CategoriaFinanceira> entities);


    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CategoriaFinanceiraDTO dto, @MappingTarget CategoriaFinanceira entity);
}
