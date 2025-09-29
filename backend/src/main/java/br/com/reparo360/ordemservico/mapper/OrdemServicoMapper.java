package br.com.reparo360.ordemservico.mapper;

import br.com.reparo360.ordemservico.dto.OrdemServicoDTO;
import br.com.reparo360.ordemservico.entity.OrdemServico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdemServicoMapper {

    /**
     * Cria a entidade a partir do DTO:
     * - ignora id e agendamento (são setados em código)
     */
    @Mapping(target = "id",           ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(source = "valorServico",   target = "valorServico")
    @Mapping(source = "valorMateriais", target = "valorMateriais")
    @Mapping(source = "kmDeslocamento", target = "kmDeslocamento")
    @Mapping(source = "observacoes",    target = "observacoes")
    @Mapping(source = "logradouro",     target = "logradouro")
    @Mapping(source = "numero",         target = "numero")
    @Mapping(source = "complemento",    target = "complemento")
    @Mapping(source = "bairro",         target = "bairro")
    @Mapping(source = "cidade",         target = "cidade")
    @Mapping(source = "uf",             target = "uf")
    @Mapping(source = "cep",            target = "cep")
    OrdemServico toEntity(OrdemServicoDTO dto);

    /**
     * Converte entidade → DTO:
     * - mapeia id → dto.id
     * - agendamento.idAgendamento → dto.agendamentoId
     * - campos de endereço vindos do Agendamento.embedded EnderecoAgendamento
     * - demais campos com nomes idênticos são copiados automaticamente
     */
    @Mapping(source = "id",                                   target = "id")
    @Mapping(source = "agendamento.idAgendamento",           target = "agendamentoId")
    @Mapping(source = "valorServico",                        target = "valorServico")
    @Mapping(source = "valorMateriais",                      target = "valorMateriais")
    @Mapping(source = "kmDeslocamento",                      target = "kmDeslocamento")
    @Mapping(source = "observacoes",                         target = "observacoes")
    @Mapping(source = "agendamento.enderecoAgendamento.logradouro", target = "logradouro")
    @Mapping(source = "agendamento.enderecoAgendamento.numero",     target = "numero")
    @Mapping(source = "agendamento.enderecoAgendamento.complemento",target = "complemento")
    @Mapping(source = "agendamento.enderecoAgendamento.bairro",     target = "bairro")
    @Mapping(source = "agendamento.enderecoAgendamento.cidade",     target = "cidade")
    @Mapping(source = "agendamento.enderecoAgendamento.uf",         target = "uf")
    @Mapping(source = "agendamento.enderecoAgendamento.cep",        target = "cep")
    OrdemServicoDTO toDTO(OrdemServico entity);

    List<OrdemServicoDTO> toDTOList(List<OrdemServico> entities);

    /**
     * Atualiza apenas campos mutáveis em uma entidade existente:
     * - ignora id e agendamento
     * - copia status, valores, km, observações e quaisquer outros campos de mesmo nome
     */
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    void updateEntityFromDTO(OrdemServicoDTO dto, @MappingTarget OrdemServico entity);
}
