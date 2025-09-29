package br.com.reparo360.financeiro.mapper;

import br.com.reparo360.financeiro.dto.ContaCaixaDTO;
import br.com.reparo360.financeiro.entity.ContaCaixa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper para ContaCaixa ↔ ContaCaixaDTO.
 */
@Mapper(componentModel = "spring")
public interface ContaCaixaMapper {

    /**
     * Converte um ContaCaixaDTO em ContaCaixa (Entity).
     * - Como o DTO não possui setter/getter para 'id', o MapStruct
     *   não tentará atribuí-lo na entidade. Se quiser forçar alguma lógica
     *   de ID, faça isso no Service.
     */
    ContaCaixa toEntity(ContaCaixaDTO dto);

    /**
     * Converte um ContaCaixa (Entity) em ContaCaixaDTO.
     * - Mapeia 'id', 'nome' e 'saldoInicial' automaticamente,
     *   pois os nomes são idênticos em DTO e em Entity.
     */
    ContaCaixaDTO toDTO(ContaCaixa entity);

    /**
     * Converte lista de Entidades → lista de DTOs
     * (o MapStruct implementa isso em cima do método toDTO).
     */
    List<ContaCaixaDTO> toDTOList(List<ContaCaixa> entities);

    /**
     * Atualiza um ContaCaixa (Entity) existente a partir de um ContaCaixaDTO.
     *
     * • O MapStruct vai copiar, por convenção, todos os campos de mesmo nome
     *   (no caso, 'nome' e 'saldoInicial'), mas **não tentará mexer em 'id'**,
     *   pois o DTO não possui propriedade ‘id’ com mesmo nome (ou seja, não haverá
     *   conflito).
     * • Se você quisesse ignorar explicitamente algum outro campo, poderia usar
     *   @Mapping(target="campoX", ignore=true), mas não é necessário para o 'id'.
     */
    void updateEntityFromDTO(ContaCaixaDTO dto, @MappingTarget ContaCaixa entity);
}
