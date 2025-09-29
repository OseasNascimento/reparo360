package br.com.reparo360.service;

import br.com.reparo360.dto.TecnicoDTO;
import java.util.List;

public interface TecnicoService {
    TecnicoDTO create(TecnicoDTO dto);
    List<TecnicoDTO> findAll();
    TecnicoDTO findById(Long id);
    TecnicoDTO update(Long id, TecnicoDTO dto);
    void delete(Long id);
}