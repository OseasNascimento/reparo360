package br.com.reparo360.financeiro.mapper;

import br.com.reparo360.financeiro.dto.ApuracaoServicoDTO;
import br.com.reparo360.financeiro.entity.ApuracaoServico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper para ApuracaoServico ↔ ApuracaoServicoDTO.
 */
@Mapper(componentModel = "spring")
public interface ApuracaoServicoMapper {

    @Mapping(source = "ordemServico.id", target = "ordemServicoId")
    ApuracaoServicoDTO toDTO(ApuracaoServico entity);

    @Mapping(target = "ordemServico", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receitaLiquida", ignore = true)
    @Mapping(target = "dataApuracao", ignore = true)
    ApuracaoServico toEntity(ApuracaoServicoDTO dto);

    List<ApuracaoServicoDTO> toDTOList(List<ApuracaoServico> entities);
}
