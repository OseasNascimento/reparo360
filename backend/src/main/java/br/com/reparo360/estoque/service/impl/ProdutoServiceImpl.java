package br.com.reparo360.estoque.service.impl;

import br.com.reparo360.estoque.dto.ProdutoDTO;
import br.com.reparo360.estoque.entity.Produto;
import br.com.reparo360.estoque.mapper.ProdutoMapper;
import br.com.reparo360.estoque.repository.ProdutoRepository;
import br.com.reparo360.estoque.service.ProdutoService;
import br.com.reparo360.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço de Produto.
 */
@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Override
    public ProdutoDTO criarProduto(ProdutoDTO dto) {
        // Verifica se já existe produto com mesmo nome (opcional)
        produtoRepository.findByNome(dto.getNome())
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Já existe um produto com nome: " + dto.getNome());
                });

        Produto entity = produtoMapper.toEntity(dto);
        // Se quiser inicializar estoque com o valor enviado em dto, já está no mapeamento.
        Produto salvo = produtoRepository.save(entity);
        return produtoMapper.toDTO(salvo);
    }

    @Override
    @Transactional
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO dto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id " + id));

        // Atualiza campos (exceto id)
        produtoMapper.updateEntityFromDTO(dto, existente);
        Produto atualizado = produtoRepository.save(existente);
        return produtoMapper.toDTO(atualizado);
    }

    @Override
    public ProdutoDTO buscarPorId(Long id) {
        Produto entidade = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id " + id));
        return produtoMapper.toDTO(entidade);
    }

    @Override
    public List<ProdutoDTO> listarTodos() {
        List<Produto> lista = produtoRepository.findAll();
        return produtoMapper.toDTOList(lista);
    }

    @Override
    @Transactional
    public void excluirProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id " + id));
        produtoRepository.delete(produto);
    }
}
