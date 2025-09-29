package br.com.reparo360.estoque.service;

import br.com.reparo360.estoque.dto.ProdutoDTO;
import java.util.List;

/**
 * Interface de serviço para Produto.
 */
public interface ProdutoService {

    ProdutoDTO criarProduto(ProdutoDTO dto);

    ProdutoDTO atualizarProduto(Long id, ProdutoDTO dto);

    ProdutoDTO buscarPorId(Long id);

    List<ProdutoDTO> listarTodos();

    void excluirProduto(Long id);
}
