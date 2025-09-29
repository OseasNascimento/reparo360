package br.com.reparo360.estoque.mapper;

import br.com.reparo360.estoque.dto.MovimentacaoEstoqueDTO;
import br.com.reparo360.estoque.entity.MovimentacaoEstoque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

/**
 * Mapper para converter entre MovimentacaoEstoque ↔ MovimentacaoEstoqueDTO.
 */
@Mapper(componentModel = "spring")
public interface MovimentacaoEstoqueMapper {


    @Mapping(source = "produto.id",       target = "produtoId")
    @Mapping(source = "id",              target = "id")
    @Mapping(source = "tipo",            target = "tipo")
    @Mapping(source = "quantidade",      target = "quantidade")
    @Mapping(source = "dataMovimentacao", target = "dataMovimentacao")
    @Mapping(source = "descricao",       target = "descricao")
    MovimentacaoEstoqueDTO toDTO(MovimentacaoEstoque entity);


    @Mapping(target = "id",              ignore = true)
    @Mapping(target = "produto",         ignore = true)
    @Mapping(source = "tipo",            target = "tipo")
    @Mapping(source = "quantidade",      target = "quantidade")
    @Mapping(source = "dataMovimentacao", target = "dataMovimentacao")
    @Mapping(source = "descricao",       target = "descricao")
    MovimentacaoEstoque toEntity(MovimentacaoEstoqueDTO dto);

    List<MovimentacaoEstoqueDTO> toDTOList(List<MovimentacaoEstoque> entities);
}
