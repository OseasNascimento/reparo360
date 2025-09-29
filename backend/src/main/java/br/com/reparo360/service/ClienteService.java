package br.com.reparo360.service;

import br.com.reparo360.dto.ClienteDTO;
import br.com.reparo360.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    ClienteDTO create(ClienteDTO dto);
    List<ClienteDTO> findAll();
    ClienteDTO findById(Long id);
    Optional<ClienteDTO> findByEmail(String email);
    ClienteDTO update(Long id, ClienteDTO dto);
    void delete(Long id);
    ClienteDTO saveOrUpdate(ClienteDTO dto);
    Cliente findEntityById(Long id);
}