package br.com.reparo360.financeiro.service.impl;

import br.com.reparo360.financeiro.dto.LancamentoFinanceiroDTO;
import br.com.reparo360.financeiro.entity.LancamentoFinanceiro;
import br.com.reparo360.financeiro.entity.TipoTransacao;
import br.com.reparo360.financeiro.mapper.LancamentoFinanceiroMapper;
import br.com.reparo360.financeiro.repository.LancamentoFinanceiroRepository;
import br.com.reparo360.financeiro.repository.ContaCaixaRepository;
import br.com.reparo360.financeiro.repository.CategoriaFinanceiraRepository;
import br.com.reparo360.financeiro.service.LancamentoFinanceiroService;
import br.com.reparo360.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de LancamentoFinanceiro (CRUD + consultas).
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LancamentoFinanceiroServiceImpl implements LancamentoFinanceiroService {

    private final LancamentoFinanceiroRepository lancRepo;
    private final ContaCaixaRepository contaRepo;
    private final CategoriaFinanceiraRepository categoriaRepo;
    private final LancamentoFinanceiroMapper mapper;

    @Override
    public LancamentoFinanceiroDTO criarLancamento(LancamentoFinanceiroDTO dto) {
        var conta = contaRepo.findById(dto.getContaCaixaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ContaCaixa não encontrada com id " + dto.getContaCaixaId()));

        var categoria = categoriaRepo.findById(dto.getCategoriaFinanceiraId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CategoriaFinanceira não encontrada com id " + dto.getCategoriaFinanceiraId()));

        // Converte DTO → Entidade (campos valor, dataLancamento (possivelmente nulo), tipoTransacao, descricao)
        LancamentoFinanceiro entity = mapper.toEntity(dto);

        // Se dataLancamento vier nulo, preenche com o timestamp atual
        if (entity.getDataLancamento() == null) {
            entity.setDataLancamento(LocalDateTime.now());
        }

        // Associa ContaCaixa e CategoriaFinanceira, que são obrigatórios na entidade
        entity.setContaCaixa(conta);
        entity.setCategoriaFinanceira(categoria);

        // Persiste no banco
        LancamentoFinanceiro salvo = lancRepo.save(entity);
        return mapper.toDTO(salvo);
    }

    @Override
    public LancamentoFinanceiroDTO atualizarLancamento(Long id, LancamentoFinanceiroDTO dto) {
        LancamentoFinanceiro existente = lancRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "LancamentoFinanceiro não encontrado com id " + id));

        // Atualiza campos mutáveis (valor, dataLancamento, tipoTransacao, descricao)
        mapper.updateEntityFromDTO(dto, existente);

        // Se dataLancamento foi passado como nulo no DTO,
        // ENTÃO “updateEntityFromDTO” deixou existente.getDataLancamento() inalterado
        // (ou seja, não será nulo). Se chegou explicitamente como null,
        // mantemos a data antiga. Para não sobrescrever com null, não fazemos nada aqui.

        // Caso mude a conta ou categoria, busca novos objetos:
        if (!existente.getContaCaixa().getId().equals(dto.getContaCaixaId())) {
            var novaConta = contaRepo.findById(dto.getContaCaixaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "ContaCaixa não encontrada com id " + dto.getContaCaixaId()));
            existente.setContaCaixa(novaConta);
        }
        if (!existente.getCategoriaFinanceira().getId().equals(dto.getCategoriaFinanceiraId())) {
            var novaCat = categoriaRepo.findById(dto.getCategoriaFinanceiraId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "CategoriaFinanceira não encontrada com id " + dto.getCategoriaFinanceiraId()));
            existente.setCategoriaFinanceira(novaCat);
        }

        LancamentoFinanceiro atualizado = lancRepo.save(existente);
        return mapper.toDTO(atualizado);
    }

    @Override
    public LancamentoFinanceiroDTO buscarPorId(Long id) {
        LancamentoFinanceiro entity = lancRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "LancamentoFinanceiro não encontrado com id " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<LancamentoFinanceiroDTO> listarTodos() {
        return lancRepo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void excluirLancamento(Long id) {
        LancamentoFinanceiro existente = lancRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "LancamentoFinanceiro não encontrado com id " + id));
        lancRepo.delete(existente);
    }

    @Override
    public List<LancamentoFinanceiroDTO> listarPorContaCaixa(Long contaCaixaId) {
        contaRepo.findById(contaCaixaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ContaCaixa não encontrada com id " + contaCaixaId));

        return lancRepo.findByContaCaixaId(contaCaixaId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LancamentoFinanceiroDTO> listarPorTipo(TipoTransacao tipoTransacao) {
        return lancRepo.findByTipoTransacao(tipoTransacao)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
