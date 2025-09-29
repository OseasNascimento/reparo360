package br.com.reparo360.financeiro.service.impl;

import br.com.reparo360.config.service.ConfigService;
import br.com.reparo360.financeiro.entity.ApuracaoServico;
import br.com.reparo360.financeiro.entity.CategoriaFinanceira;
import br.com.reparo360.financeiro.entity.ContaCaixa;
import br.com.reparo360.financeiro.entity.LancamentoFinanceiro;
import br.com.reparo360.financeiro.entity.TipoTransacao;
import br.com.reparo360.financeiro.repository.ApuracaoServicoRepository;
import br.com.reparo360.financeiro.repository.CategoriaFinanceiraRepository;
import br.com.reparo360.financeiro.repository.ContaCaixaRepository;
import br.com.reparo360.financeiro.repository.LancamentoFinanceiroRepository;
import br.com.reparo360.financeiro.service.GestaoFinanceiraService;
import br.com.reparo360.ordemservico.entity.OrdemServico;
import br.com.reparo360.ordemservico.event.OrdemServicoStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GestaoFinanceiraServiceImpl implements GestaoFinanceiraService {

    // nomes/categorias fixos (devem existir/ser criados com estes nomes)
    private static final String NOME_CONTA_PADRAO = "CAIXA_PRINCIPAL";
    private static final String CAT_SERVICO       = "SERVICO";
    private static final String CAT_MATERIAL      = "MATERIAL";
    private static final String CAT_DESLOCAMENTO  = "DESLOCAMENTO";

    // chave de configuração
    private static final String PARAM_CUSTO_POR_KM = "CUSTO_POR_KM";

    private final ConfigService configService;
    private final ContaCaixaRepository contaRepo;
    private final CategoriaFinanceiraRepository categoriaRepo;
    private final LancamentoFinanceiroRepository lancRepo;
    private final ApuracaoServicoRepository apuracaoRepo;

    @Override
    public void registrarOSAssinada(OrdemServicoStatusChangedEvent event) {
        final Long osId                = event.getOsId();
        final BigDecimal valorServEvt  = event.getValorServico();
        final BigDecimal valorMatEvt   = event.getValorMateriais();
        final Integer km               = event.getKmDeslocamento();

        // normaliza nulos
        BigDecimal valorServico        = valorServEvt != null ? valorServEvt : BigDecimal.ZERO;
        BigDecimal valorMateriaisTotal = valorMatEvt  != null ? valorMatEvt  : BigDecimal.ZERO;

        // conta/categorias (cria caso não exista)
        ContaCaixa contaCaixa               = getOrCreateConta(NOME_CONTA_PADRAO);
        CategoriaFinanceira catServico      = getOrCreateCategoria(CAT_SERVICO, "Receitas de serviços");
        CategoriaFinanceira catMateriais    = getOrCreateCategoria(CAT_MATERIAL, "Custos de materiais");
        CategoriaFinanceira catDeslocamento = getOrCreateCategoria(CAT_DESLOCAMENTO, "Custos de deslocamento");

        // RECEITA (serviço)
        if (valorServico.compareTo(BigDecimal.ZERO) > 0) {
            lancRepo.save(LancamentoFinanceiro.builder()
                    .contaCaixa(contaCaixa)
                    .categoriaFinanceira(catServico)
                    .tipoTransacao(TipoTransacao.RECEITA)
                    .valor(valorServico)
                    .dataLancamento(LocalDateTime.now())
                    .descricao("Receita - OS assinada ID: " + osId)
                    .build());
        }

        // DESPESA (materiais)
        if (valorMateriaisTotal.compareTo(BigDecimal.ZERO) > 0) {
            lancRepo.save(LancamentoFinanceiro.builder()
                    .contaCaixa(contaCaixa)
                    .categoriaFinanceira(catMateriais)
                    .tipoTransacao(TipoTransacao.DESPESA)
                    .valor(valorMateriaisTotal)
                    .dataLancamento(LocalDateTime.now())
                    .descricao("Despesa de Materiais - OS ID: " + osId)
                    .build());
        }

        // DESPESA (deslocamento por km) — custo via parâmetro; fallback 1.50
        if (km != null && km > 0) {
            BigDecimal custoKm   = configService.getDecimal(PARAM_CUSTO_POR_KM, new BigDecimal("1.50"));
            BigDecimal valorDesl = custoKm.multiply(BigDecimal.valueOf(km));

            lancRepo.save(LancamentoFinanceiro.builder()
                    .contaCaixa(contaCaixa)
                    .categoriaFinanceira(catDeslocamento)
                    .tipoTransacao(TipoTransacao.DESPESA)
                    .valor(valorDesl)
                    .dataLancamento(LocalDateTime.now())
                    .descricao("Despesa de Deslocamento - OS ID: " + osId + " (" + km + " km)")
                    .build());

            valorMateriaisTotal = valorMateriaisTotal.add(valorDesl);
        }

        // apuração (serviço - custos)
        BigDecimal receitaLiquida = valorServico.subtract(valorMateriaisTotal);

        apuracaoRepo.save(ApuracaoServico.builder()
                .ordemServico(OrdemServico.builder().id(osId).build())
                .valorServico(valorServico)
                .valorMateriais(valorMateriaisTotal)
                .kmDeslocamento(km)
                .receitaLiquida(receitaLiquida)
                .dataApuracao(LocalDateTime.now())
                .build());
    }

    @Override
    public void registrarOSRecusada(OrdemServicoStatusChangedEvent event) {
        final Long osId = event.getOsId();
        BigDecimal valorMat = event.getValorMateriais() != null ? event.getValorMateriais() : BigDecimal.ZERO;
        final Integer km    = event.getKmDeslocamento();

        ContaCaixa contaCaixa               = getOrCreateConta(NOME_CONTA_PADRAO);
        CategoriaFinanceira catMateriais    = getOrCreateCategoria(CAT_MATERIAL, "Custos de materiais");
        CategoriaFinanceira catDeslocamento = getOrCreateCategoria(CAT_DESLOCAMENTO, "Custos de deslocamento");

        // materiais
        if (valorMat.compareTo(BigDecimal.ZERO) > 0) {
            lancRepo.save(LancamentoFinanceiro.builder()
                    .contaCaixa(contaCaixa)
                    .categoriaFinanceira(catMateriais)
                    .tipoTransacao(TipoTransacao.DESPESA)
                    .valor(valorMat)
                    .dataLancamento(LocalDateTime.now())
                    .descricao("Despesa Estorno Materiais - OS recusada ID: " + osId)
                    .build());
        }

        // deslocamento (opcional)
        if (km != null && km > 0) {
            BigDecimal custoKm   = configService.getDecimal(PARAM_CUSTO_POR_KM, new BigDecimal("1.50"));
            BigDecimal valorDesl = custoKm.multiply(BigDecimal.valueOf(km));

            lancRepo.save(LancamentoFinanceiro.builder()
                    .contaCaixa(contaCaixa)
                    .categoriaFinanceira(catDeslocamento)
                    .tipoTransacao(TipoTransacao.DESPESA)
                    .valor(valorDesl)
                    .dataLancamento(LocalDateTime.now())
                    .descricao("Despesa Deslocamento - OS recusada ID: " + osId)
                    .build());

            valorMat = valorMat.add(valorDesl);
        }

        // apuração negativa
        if (valorMat.compareTo(BigDecimal.ZERO) > 0) {
            apuracaoRepo.save(ApuracaoServico.builder()
                    .ordemServico(OrdemServico.builder().id(osId).build())
                    .valorServico(BigDecimal.ZERO)
                    .valorMateriais(valorMat)
                    .kmDeslocamento(km)
                    .receitaLiquida(BigDecimal.ZERO.subtract(valorMat))
                    .dataApuracao(LocalDateTime.now())
                    .build());
        }
    }

    // ----------------- helpers -----------------

    private ContaCaixa getOrCreateConta(String nome) {
        return contaRepo.findByNome(nome).orElseGet(() -> {
            ContaCaixa c = new ContaCaixa();
            c.setNome(nome);
            c.setSaldoInicial(BigDecimal.ZERO);
            ContaCaixa saved = contaRepo.save(c);
            log.info("Criada ContaCaixa '{}' automaticamente", nome);
            return saved;
        });
    }

    private CategoriaFinanceira getOrCreateCategoria(String nome, String descricaoPadrao) {
        return categoriaRepo.findByNomeIgnoreCase(nome).orElseGet(() -> {
            CategoriaFinanceira cat = new CategoriaFinanceira();
            cat.setNome(nome);
            cat.setDescricao(descricaoPadrao);
            CategoriaFinanceira saved = categoriaRepo.save(cat);
            log.info("Criada CategoriaFinanceira '{}' automaticamente", nome);
            return saved;
        });
    }
}
