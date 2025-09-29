package br.com.reparo360.mapper;

import br.com.reparo360.dto.ClienteDTO;
import br.com.reparo360.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "endereco.logradouro",  source = "logradouro")
    @Mapping(target = "endereco.numero",      source = "numero")
    @Mapping(target = "endereco.complemento", source = "complemento")
    @Mapping(target = "endereco.bairro",      source = "bairro")
    @Mapping(target = "endereco.cidade",      source = "cidade")
    @Mapping(target = "endereco.uf",          source = "uf")
    @Mapping(target = "endereco.cep",         source = "cep")
    @Mapping(target = "idCliente",            ignore = true)
    Cliente toEntity(ClienteDTO dto);


    @Mapping(target = "logradouro",   source = "endereco.logradouro")
    @Mapping(target = "numero",       source = "endereco.numero")
    @Mapping(target = "complemento",  source = "endereco.complemento")
    @Mapping(target = "bairro",       source = "endereco.bairro")
    @Mapping(target = "cidade",       source = "endereco.cidade")
    @Mapping(target = "uf",           source = "endereco.uf")
    @Mapping(target = "cep",          source = "endereco.cep")
    ClienteDTO toDto(Cliente entity);

    List<ClienteDTO> toDtoList(List<Cliente> entidades);

    void updateEntityFromDto(ClienteDTO dto, @MappingTarget Cliente entity);


}
