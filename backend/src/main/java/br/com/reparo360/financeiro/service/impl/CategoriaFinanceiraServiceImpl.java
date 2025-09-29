package br.com.reparo360.financeiro.service.impl;

import br.com.reparo360.financeiro.dto.CategoriaFinanceiraDTO;
import br.com.reparo360.financeiro.entity.CategoriaFinanceira;
import br.com.reparo360.financeiro.mapper.CategoriaFinanceiraMapper;
import br.com.reparo360.financeiro.repository.CategoriaFinanceiraRepository;
import br.com.reparo360.financeiro.service.CategoriaFinanceiraService;
import br.com.reparo360.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço de CategoriaFinanceira (CRUD).
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaFinanceiraServiceImpl implements CategoriaFinanceiraService {

    private final CategoriaFinanceiraRepository categoriaRepo;
    private final CategoriaFinanceiraMapper mapper;

    @Override
    public CategoriaFinanceiraDTO criarCategoria(CategoriaFinanceiraDTO dto) {
        // Verifica se já existe categoria com mesmo nome
        categoriaRepo.findByNomeIgnoreCase(dto.getNome())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Categoria com nome '" + dto.getNome() + "' já existe");
                });
        CategoriaFinanceira entity = mapper.toEntity(dto);
        CategoriaFinanceira salva = categoriaRepo.save(entity);
        return mapper.toDTO(salva);
    }

    @Override
    public CategoriaFinanceiraDTO atualizarCategoria(Long id, CategoriaFinanceiraDTO dto) {
        CategoriaFinanceira existente = categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaFinanceira não encontrada com id " + id));

        // Verifica duplicata somente se o nome realmente mudou
        if (!existente.getNome().equals(dto.getNome())) {
            categoriaRepo.findByNomeIgnoreCase(dto.getNome())
                    .ifPresent(c -> {
                        throw new IllegalArgumentException(
                                "Categoria com nome '" + dto.getNome() + "' já existe");
                    });
        }
        // Atualiza apenas campos válidos (exceto id e nome único, se quiser manter nome imutável, comente a linha abaixo)
        mapper.updateEntityFromDTO(dto, existente);

        CategoriaFinanceira atualizado = categoriaRepo.save(existente);
        return mapper.toDTO(atualizado);
    }

    @Override
    public CategoriaFinanceiraDTO buscarPorId(Long id) {
        CategoriaFinanceira entity = categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaFinanceira não encontrada com id " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<CategoriaFinanceiraDTO> listarTodas() {
        List<CategoriaFinanceira> lista = categoriaRepo.findAll();
        return mapper.toDTOList(lista);
    }

    @Override
    public void excluirCategoria(Long id) {
        CategoriaFinanceira existente = categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaFinanceira não encontrada com id " + id));
        categoriaRepo.delete(existente);
    }
}
