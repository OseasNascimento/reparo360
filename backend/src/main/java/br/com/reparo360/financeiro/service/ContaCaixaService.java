package br.com.reparo360.financeiro.service;

import br.com.reparo360.financeiro.dto.ContaCaixaDTO;

import java.util.List;

/**
 * Interface de serviço para ContaCaixa (CRUD completo).
 */
public interface ContaCaixaService {

    /**
     * Cria uma nova ContaCaixa.
     */
    ContaCaixaDTO criarContaCaixa(ContaCaixaDTO dto);

    /**
     * Atualiza ContaCaixa existente, por ID.
     */
    ContaCaixaDTO atualizarContaCaixa(Long id, ContaCaixaDTO dto);

    /**
     * Busca uma ContaCaixa por ID.
     */
    ContaCaixaDTO buscarPorId(Long id);

    /**
     * Lista todas as ContasCaixa.
     */
    List<ContaCaixaDTO> listarTodas();

    /**
     * Exclui uma ContaCaixa por ID.
     */
    void excluirContaCaixa(Long id);
}
