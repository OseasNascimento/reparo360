package br.com.reparo360.estoque.mapper;

import br.com.reparo360.estoque.dto.PecaUtilizadaDTO;
import br.com.reparo360.estoque.entity.PecaUtilizada;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PecaUtilizadaMapper {

    /**
     * Converte Entidade → DTO:
     *   - mapeia produto.id → produtoId
     *   - mapeia quantidade automaticamente (mesmo nome em Entity e DTO)
     */
    @Mapping(source = "ordemServico.id", target = "ordemServicoId")
    @Mapping(source = "produto.id", target = "produtoId")
    PecaUtilizadaDTO toDto(PecaUtilizada entity);

    /**
     * Converte DTO → Entidade:
     *   - ignora ID porque será gerado pelo banco
     *   - ignora produto e ordemServico: essas associações
     *     serão feitas manualmente no service (vindo da camada de negócio)
     *   - mapeia quantidade automaticamente (campo mesmo nome)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "ordemServico", ignore = true)
    PecaUtilizada toEntity(PecaUtilizadaDTO dto);

    /**
     * Converte lista de Entidades → lista de DTOs.
     * O MapStruct implementa isso automaticamente com base em toDto(...).
     */
    List<PecaUtilizadaDTO> toDTOList(List<PecaUtilizada> entities);

    /**
     * Se precisar converter lista de DTOs → lista de Entidades,
     * use este método. Ainda assim, lembre-se de que “produto” e “ordemServico”
     * devem ser atribuídos manualmente no Service.
     */
    List<PecaUtilizada> toEntityList(List<PecaUtilizadaDTO> dtos);
}
