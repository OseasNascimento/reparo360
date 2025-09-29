package br.com.reparo360.service;

import br.com.reparo360.dto.ServicoDTO;
import java.util.List;

public interface ServicoService {
    ServicoDTO create(ServicoDTO dto);
    List<ServicoDTO> findAll();
    ServicoDTO findById(Long id);
    ServicoDTO update(Long id, ServicoDTO dto);
    void delete(Long id);
}