package br.com.reparo360.mapper;

import br.com.reparo360.dto.AgendamentoDTO;
import br.com.reparo360.entity.Agendamento;
import br.com.reparo360.entity.Servico;
import br.com.reparo360.entity.EnderecoAgendamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {

    @Mapping(source = "cliente.nome",               target = "nomeCliente")
    @Mapping(source = "cliente.email",              target = "email")
    @Mapping(source = "cliente.telefone",           target = "telefone")
    @Mapping(source = "idAgendamento",       target = "id")
    @Mapping(source = "cliente.idCliente",   target = "clienteId")
    @Mapping(source = "tecnico.id",          target = "tecnicoId")
    @Mapping(source = "dataAgendamento",     target = "dataAgendamento")
    @Mapping(source = "servicos",            target = "servicosId", qualifiedByName = "servicosParaIds")
    @Mapping(source = "observacoes",         target = "observacoes")
    @Mapping(source = "status",              target = "status")
    @Mapping(source = "enderecoAgendamento.logradouro", target = "logradouro")
    @Mapping(source = "enderecoAgendamento.numero",     target = "numero")
    @Mapping(source = "enderecoAgendamento.complemento",target = "complemento")
    @Mapping(source = "enderecoAgendamento.bairro",     target = "bairro")
    @Mapping(source = "enderecoAgendamento.cidade",     target = "cidade")
    @Mapping(source = "enderecoAgendamento.uf",         target = "uf")
    @Mapping(source = "enderecoAgendamento.cep",        target = "cep")
    AgendamentoDTO toDTO(Agendamento entity);

    @Mapping(target = "idAgendamento",       ignore = true)
    @Mapping(target = "cliente",             ignore = true)
    @Mapping(target = "tecnico",             ignore = true)
    @Mapping(source = "dataAgendamento",     target = "dataAgendamento")
    @Mapping(target = "servicos",            ignore = true)
    @Mapping(source = "observacoes",         target = "observacoes")
    @Mapping(source = "status",              target = "status")
    @Mapping(target = "enderecoAgendamento", expression = "java(toEnderecoEntity(dto))")
    Agendamento toEntity(AgendamentoDTO dto);

    List<AgendamentoDTO> toDTOList(List<Agendamento> entities);

    @Named("servicosParaIds")
    default List<Long> servicosParaIds(Iterable<Servico> servicos) {
        if (servicos == null) {
            return Collections.emptyList();
        }
        // cria um snapshot para evitar ConcurrentModificationException
        List<Servico> snapshot = new ArrayList<>();
        servicos.forEach(snapshot::add);
        return snapshot.stream()
                .map(Servico::getIdServico)
                .collect(Collectors.toList());
    }

    default EnderecoAgendamento toEnderecoEntity(AgendamentoDTO dto) {
        if (dto == null) {
            return null;
        }
        return EnderecoAgendamento.builder()
                .logradouro(dto.getLogradouro())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .uf(dto.getUf())
                .cep(dto.getCep())
                .build();
    }
}
