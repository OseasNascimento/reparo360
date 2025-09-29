package br.com.reparo360.estoque.service.impl;

import br.com.reparo360.estoque.dto.PecaUtilizadaDTO;
import br.com.reparo360.estoque.entity.PecaUtilizada;
import br.com.reparo360.estoque.entity.Produto;
import br.com.reparo360.estoque.mapper.PecaUtilizadaMapper;
import br.com.reparo360.estoque.repository.PecaUtilizadaRepository;
import br.com.reparo360.estoque.repository.ProdutoRepository;
import br.com.reparo360.estoque.service.PecaUtilizadaService;
import br.com.reparo360.exception.ResourceNotFoundException;
import br.com.reparo360.ordemservico.entity.OrdemServico;
import br.com.reparo360.ordemservico.repository.OrdemServicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de PecaUtilizada.
 */
@Service
@RequiredArgsConstructor
public class PecaUtilizadaServiceImpl implements PecaUtilizadaService {

    private final PecaUtilizadaRepository repo;
    private final ProdutoRepository produtoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final PecaUtilizadaMapper mapper;

    /**
     * Salva várias peças utilizadas associadas a uma Ordem de Serviço.
     *
     * @param ordemServicoId ID da Ordem de Serviço a que as peças pertencem.
     * @param dtos           Lista de PecaUtilizadaDTO a serem salvas.
     * @return Lista de PecaUtilizadaDTO já armazenadas, incluindo IDs gerados.
     */
    @Override
    @Transactional
    public List<PecaUtilizadaDTO> salvarTodas(Long ordemServicoId, List<PecaUtilizadaDTO> dtos) {
        // 1. Verifica se a Ordem de Serviço existe
        OrdemServico ordem = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ordem de Serviço não encontrada para id " + ordemServicoId));

        // 2. Para cada DTO, converte em entidade e atribui produto + ordem
        List<PecaUtilizada> entidades = dtos.stream().map(dto -> {
            // 2.1. Verifica se o Produto existe
            Produto produto = produtoRepository.findById(dto.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Produto não encontrado para id " + dto.getProdutoId()));

            // 2.2. Converte o DTO em Entidade (mapper toEntity deve mapear descrição, quantidade e produtoId)
            PecaUtilizada entity = mapper.toEntity(dto);

            // 2.3. Atribui o Produto carregado e a Ordem de Serviço carregada
            entity.setProduto(produto);
            entity.setOrdemServico(ordem);
            return entity;
        }).collect(Collectors.toList());

        // 3. Salva todas as entidades em lote
        List<PecaUtilizada> salvas = repo.saveAll(entidades);

        // 4. Converte o resultado em lista de DTO e retorna
        //    -> Aqui pressupomos que o mapper tem o método toDTOList(List<PecaUtilizada>)
        return mapper.toDTOList(salvas);
        /*
          Caso seu mapper NÃO tenha toDTOList, faça manualmente:
            return salvas.stream()
                         .map(mapper::toDTO)
                         .collect(Collectors.toList());
         */
    }

    /**
     * Lista todas as peças utilizadas associadas a uma determinada Ordem de Serviço.
     *
     * @param ordemServicoId ID da Ordem de Serviço.
     * @return Lista de PecaUtilizadaDTO correspondentes.
     */
    @Override
    public List<PecaUtilizadaDTO> listarPorOrdemServico(Long ordemServicoId) {
        // 1. Verifica se a Ordem de Serviço existe
        ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ordem de Serviço não encontrada para id " + ordemServicoId));

        // 2. Busca todas as PecaUtilizada pelo campo ordemServico.id
        List<PecaUtilizada> lista = repo.findByOrdemServicoId(ordemServicoId);

        // 3. Converte em lista de DTO e retorna
        return mapper.toDTOList(lista);
        /*
          Caso não exista toDTOList, faça:
            return lista.stream()
                        .map(mapper::toDTO)
                        .collect(Collectors.toList());
         */
    }
}
