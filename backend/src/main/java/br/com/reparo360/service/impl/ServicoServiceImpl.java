package br.com.reparo360.service.impl;

import br.com.reparo360.dto.ServicoDTO;
import br.com.reparo360.mapper.ServicoMapper;
import br.com.reparo360.entity.Servico;
import br.com.reparo360.repository.ServicoRepository;
import br.com.reparo360.service.ServicoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoServiceImpl implements ServicoService {

    private final ServicoRepository repo;
    private final ServicoMapper mapper;

    @Override
    @Transactional
    public ServicoDTO create(ServicoDTO dto) {
        Servico nova = mapper.toEntity(dto);  // id é ignorado
        Servico salvo = repo.save(nova);
        return mapper.toDTO(salvo);
    }

    @Override
    public List<ServicoDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public ServicoDTO findById(Long id) {
        Servico s = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));
        return mapper.toDTO(s);
    }

    @Override
    @Transactional
    public ServicoDTO update(Long id, ServicoDTO dto) {
        Servico existente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));
        mapper.updateEntityFromDto(dto, existente);
        Servico salvo = repo.save(existente);
        return mapper.toDTO(salvo);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
