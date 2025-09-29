package br.com.reparo360.dashboard.service.impl;

import br.com.reparo360.dashboard.dto.ResumoDashboardDTO;
import br.com.reparo360.dashboard.service.DashboardService;
import br.com.reparo360.financeiro.repository.ContaCaixaRepository;
import br.com.reparo360.ordemservico.entity.StatusOrdemServico;
import br.com.reparo360.ordemservico.repository.OrdemServicoRepository;
import br.com.reparo360.repository.ClienteRepository;
import br.com.reparo360.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrdemServicoRepository osRepo;
    private final ClienteRepository clienteRepo;
    private final ContaCaixaRepository caixaRepo;
    private final TecnicoRepository tecnicoRepo;

    @Override
    public Long getTotalServicos() {
        return osRepo.count();
    }

    @Override
    public Double getMediaServicosPorDia() {
        return Optional.ofNullable(osRepo.mediaServicosPorDia()).orElse(0.0);
    }

    @Override
    public Long getServicosConcluidos() {
        return osRepo.countByStatus(StatusOrdemServico.CONCLUIDA);
    }

    @Override
    public Long getServicosCancelados() {
        return osRepo.countByStatus(StatusOrdemServico.CANCELADA);
    }

    @Override
    public Long getServicosFinalizados() {
        return osRepo.countByStatus(StatusOrdemServico.ASSINADA);
    }

    @Override
    public Long getClientesAtivos() {
        // Se preferir contar clientes diretamente, troque por clienteRepo.count().
        return osRepo.countDistinctClientes();
    }

    @Override
    public Map<String, Long> getClientesPorOrigem() {
        Map<String, Long> mapa = new HashMap<>();
        clienteRepo.countByOrigem().forEach(row ->
                mapa.put((String) row[0], asLong(row[1], 0L))
        );
        return mapa;
    }

    @Override
    public Map<String, Long> getOrdensPorStatus() {
        Map<String, Long> mapa = new HashMap<>();
        osRepo.countGroupByStatus().forEach(row ->
                mapa.put(((StatusOrdemServico) row[0]).name(), asLong(row[1], 0L))
        );
        return mapa;
    }

    @Override
    public Map<String, Long> getOrdensPorServico() {
        Map<String, Long> mapa = new HashMap<>();
        osRepo.countGroupByServico().forEach(row ->
                mapa.put((String) row[0], asLong(row[1], 0L))
        );
        return mapa;
    }

    @Override
    public Double getValorTotalOrdens() {
        // soma apenas as ordens ASSINADA
        return Optional.ofNullable(
                osRepo.sumValorTotal(StatusOrdemServico.ASSINADA)
        ).orElse(0.0);
    }

    @Override
    public Double getCustoTotalOrdens() {
        // soma apenas as ordens ASSINADA
        return Optional.ofNullable(
                osRepo.sumCustoTotal(StatusOrdemServico.ASSINADA)
        ).orElse(0.0);
    }

    @Override
    public Double getReceitaTotal() {
        // receita = soma do valor total das OS assinadas
        return Optional.ofNullable(
                osRepo.sumValorTotal(StatusOrdemServico.ASSINADA)
        ).orElse(0.0);
    }

    @Override
    public Double getDespesasTotais() {
        return Optional.ofNullable(caixaRepo.sumDespesas()).orElse(0.0);
    }

    @Override
    public Map<String, Double> getLucroPorMes() {
        // Monta a partir da query e PREENCHE todos os 12 meses do ano atual com 0.0 quando faltar.
        Map<String, Double> mapa = new HashMap<>();

        List<Object[]> rows = osRepo.getLucroPorMes(); // esperado: [ano, mes, lucro]
        for (Object[] row : rows) {
            if (row == null || row.length < 3) continue;

            int ano   = asInt(row[0], -1);
            int mes   = asInt(row[1], -1);
            double v  = asDouble(row[2], 0.0);

            if (ano > 0 && mes >= 1 && mes <= 12) {
                String key = String.format("%04d-%02d", ano, mes);
                mapa.merge(key, v, Double::sum);
            }
        }

        // Garante 12 pontos para o ano atual (evita gráfico "vazio")
        int anoAtual = Year.now().getValue();
        for (int m = 1; m <= 12; m++) {
            String key = String.format("%04d-%02d", anoAtual, m);
            mapa.putIfAbsent(key, 0.0);
        }

        return mapa;
    }

    @Override
    public Map<String, Double> getVendasPorTecnico() {
        Map<String, Double> mapa = new HashMap<>();
        osRepo.getVendasPorTecnico().forEach(row ->
                mapa.put((String) row[0], asDouble(row[1], 0.0))
        );
        return mapa;
    }

    @Override
    public Map<String, Double> getComissoesPorTecnico() {
        Map<String, Double> mapa = new HashMap<>();
        osRepo.getComissoesPorTecnico().forEach(row ->
                mapa.put((String) row[0], asDouble(row[1], 0.0))
        );
        return mapa;
    }

    @Override
    public Map<String, Long> getServicosMaisVendidos() {
        Map<String, Long> mapa = new HashMap<>();
        osRepo.getServicosMaisVendidos().forEach(row ->
                mapa.put((String) row[0], asLong(row[1], 0L))
        );
        return mapa;
    }

    @Override
    public ResumoDashboardDTO getResumoGeral() {
        double receita = getReceitaTotal();
        double despesa = getDespesasTotais();
        double lucro   = receita - despesa;
        long total     = getTotalServicos();
        return new ResumoDashboardDTO(receita, despesa, lucro, total);
    }

    // ----------------- helpers defensivos -----------------

    private static int asInt(Object o, int def) {
        if (o == null) return def;
        if (o instanceof Number n) return n.intValue();
        try { return Integer.parseInt(o.toString()); } catch (Exception e) { return def; }
    }

    private static long asLong(Object o, long def) {
        if (o == null) return def;
        if (o instanceof Number n) return n.longValue();
        try { return Long.parseLong(o.toString()); } catch (Exception e) { return def; }
    }

    private static double asDouble(Object o, double def) {
        if (o == null) return def;
        if (o instanceof Number n) return n.doubleValue();
        try { return Double.parseDouble(o.toString()); } catch (Exception e) { return def; }
    }
}
