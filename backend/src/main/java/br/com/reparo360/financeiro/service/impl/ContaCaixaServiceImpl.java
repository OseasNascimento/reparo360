package br.com.reparo360.financeiro.service.impl;

import br.com.reparo360.financeiro.dto.ContaCaixaDTO;
import br.com.reparo360.financeiro.entity.ContaCaixa;
import br.com.reparo360.financeiro.mapper.ContaCaixaMapper;
import br.com.reparo360.financeiro.repository.ContaCaixaRepository;
import br.com.reparo360.financeiro.service.ContaCaixaService;
import br.com.reparo360.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço de ContaCaixa (CRUD).
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ContaCaixaServiceImpl implements ContaCaixaService {

    private final ContaCaixaRepository contaRepo;
    private final ContaCaixaMapper mapper;

    @Override
    public ContaCaixaDTO criarContaCaixa(ContaCaixaDTO dto) {
        // Verifica duplicidade de nome
        contaRepo.findByNome(dto.getNome())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("ContaCaixa com nome '" + dto.getNome() + "' já existe");
                });
        ContaCaixa entity = mapper.toEntity(dto);
        ContaCaixa salva = contaRepo.save(entity);
        return mapper.toDTO(salva);
    }

    @Override
    public ContaCaixaDTO atualizarContaCaixa(Long id, ContaCaixaDTO dto) {
        ContaCaixa existente = contaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContaCaixa não encontrada com id " + id));

        if (!existente.getNome().equals(dto.getNome())) {
            contaRepo.findByNome(dto.getNome())
                    .ifPresent(c -> {
                        throw new IllegalArgumentException("ContaCaixa com nome '" + dto.getNome() + "' já existe");
                    });
        }
        mapper.updateEntityFromDTO(dto, existente);

        // Atualiza campos via MapStruct (exceto ID)
        mapper.updateEntityFromDTO(dto, existente);

        ContaCaixa atualizado = contaRepo.save(existente);
        return mapper.toDTO(atualizado);
    }

    @Override
    public ContaCaixaDTO buscarPorId(Long id) {
        ContaCaixa entity = contaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContaCaixa não encontrada com id " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<ContaCaixaDTO> listarTodas() {
        List<ContaCaixa> lista = contaRepo.findAll();
        return mapper.toDTOList(lista);
    }

    @Override
    public void excluirContaCaixa(Long id) {
        ContaCaixa existente = contaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContaCaixa não encontrada com id " + id));
        contaRepo.delete(existente);
    }
}
