package br.com.reparo360.estoque.service.impl;

import br.com.reparo360.estoque.dto.MovimentacaoEstoqueDTO;
import br.com.reparo360.estoque.entity.MovimentacaoEstoque;
import br.com.reparo360.estoque.entity.Produto;
import br.com.reparo360.estoque.entity.TipoMovimentacao;
import br.com.reparo360.estoque.mapper.MovimentacaoEstoqueMapper;
import br.com.reparo360.estoque.repository.MovimentacaoEstoqueRepository;
import br.com.reparo360.estoque.repository.ProdutoRepository;
import br.com.reparo360.estoque.service.MovimentacaoEstoqueService;
import br.com.reparo360.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementação do serviço de Movimentação de Estoque.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MovimentacaoEstoqueServiceImpl implements MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movRepo;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueMapper mapper;

    /**
     * 1) registrarMovimentacao → cria a movimentação, ajusta o estoque e retorna DTO.
     */
    @Override
    public MovimentacaoEstoqueDTO registrarMovimentacao(MovimentacaoEstoqueDTO dto) {
        // 1.1) Busca o produto no banco pelo ID
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produto não encontrado com id " + dto.getProdutoId()));

        // 1.2) Converte DTO → Entidade (Mapper ignora o campo produto)
        MovimentacaoEstoque entity = mapper.toEntity(dto);
        // 1.3) Atribui o objeto Produto completo à entidade
        entity.setProduto(produto);

        // 1.4) Se a dataCandidadora vier nula, grava a hora atual
        if (entity.getDataMovimentacao() == null) {
            entity.setDataMovimentacao(LocalDateTime.now());
        }

        // 1.5) Ajusta o estoque do produto, de acordo com o tipo de movimentação
        if (dto.getTipo() == null) {
            throw new IllegalArgumentException("Tipo de movimentação não pode ser nulo");
        }
        if (dto.getTipo().equals(TipoMovimentacao.SAIDA)) {
            int novaQtd = produto.getQuantidadeEstoque() - dto.getQuantidade();
            if (novaQtd < 0) {
                throw new IllegalArgumentException("Estoque insuficiente para saída");
            }
            produto.setQuantidadeEstoque(novaQtd);
        } else { // ENTRADA
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + dto.getQuantidade());
        }
        produtoRepository.save(produto);

        // 1.6) Persiste a movimentação no banco
        MovimentacaoEstoque salva = movRepo.save(entity);

        // 1.7) Retorna o DTO do objeto salvo (que já terá ID e produtoId preenchidos)
        return mapper.toDTO(salva);
    }

    /**
     * 2) listarPorProduto → pesquisa todas as movimentações de um produto pelo ID.
     */
    @Override
    public List<MovimentacaoEstoqueDTO> listarPorProduto(Long produtoId) {
        // Garante que o produto exista
        produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produto não encontrado com id " + produtoId));

        // Busca todas as movimentações e converte para DTO
        List<MovimentacaoEstoque> lista = movRepo.findByProdutoId(produtoId);
        return mapper.toDTOList(lista);
    }

    /**
     * 3) listarTodas → retorna todas as movimentações de estoque.
     */
    @Override
    public List<MovimentacaoEstoqueDTO> listarTodas() {
        List<MovimentacaoEstoque> lista = movRepo.findAll();
        return mapper.toDTOList(lista);
    }
}
