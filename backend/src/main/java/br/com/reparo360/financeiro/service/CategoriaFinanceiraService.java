package br.com.reparo360.financeiro.service;

import br.com.reparo360.financeiro.dto.CategoriaFinanceiraDTO;

import java.util.List;

/**
 * Interface de serviço para CategoriaFinanceira (CRUD completo).
 */
public interface CategoriaFinanceiraService {

    /**
     * Cria uma nova categoria financeira.
     */
    CategoriaFinanceiraDTO criarCategoria(CategoriaFinanceiraDTO dto);

    /**
     * Atualiza uma categoria existente, por ID.
     */
    CategoriaFinanceiraDTO atualizarCategoria(Long id, CategoriaFinanceiraDTO dto);

    /**
     * Busca uma categoria por ID.
     */
    CategoriaFinanceiraDTO buscarPorId(Long id);

    /**
     * Lista todas as categorias financeiras.
     */
    List<CategoriaFinanceiraDTO> listarTodas();

    /**
     * Exclui uma categoria por ID.
     */
    void excluirCategoria(Long id);
}
