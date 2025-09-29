package br.com.reparo360.financeiro.service.impl;

import br.com.reparo360.financeiro.dto.ApuracaoServicoDTO;
import br.com.reparo360.financeiro.entity.ApuracaoServico;
import br.com.reparo360.financeiro.mapper.ApuracaoServicoMapper;
import br.com.reparo360.financeiro.repository.ApuracaoServicoRepository;
import br.com.reparo360.financeiro.service.ApuracaoServicoService;
import br.com.reparo360.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de ApuracaoServico (somente leitura).
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ApuracaoServicoServiceImpl implements ApuracaoServicoService {

    private final ApuracaoServicoRepository apuracaoRepo;
    private final ApuracaoServicoMapper mapper;

    @Override
    public List<ApuracaoServicoDTO> listarTodas() {
        List<ApuracaoServico> lista = apuracaoRepo.findAll();
        return lista.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApuracaoServicoDTO buscarPorId(Long id) {
        ApuracaoServico entity = apuracaoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ApuracaoServico não encontrada com id " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<ApuracaoServicoDTO> listarPorOrdemServico(Long ordemServicoId) {
        List<ApuracaoServico> lista = apuracaoRepo.findByOrdemServicoId(ordemServicoId);
        return lista.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
