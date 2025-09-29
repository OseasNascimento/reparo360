package br.com.reparo360.dashboard.service;

import br.com.reparo360.dashboard.dto.ResumoDashboardDTO;

import java.util.Map;

public interface DashboardService {

    Long getTotalServicos();

    Double getMediaServicosPorDia();

    Long getServicosConcluidos();

    Long getServicosCancelados();

    Long getServicosFinalizados();

    Long getClientesAtivos();

    Map<String, Long> getClientesPorOrigem();

    Map<String, Long> getOrdensPorStatus();

    Map<String, Long> getOrdensPorServico();

    Double getValorTotalOrdens();

    Double getCustoTotalOrdens();

    Double getReceitaTotal();

    Double getDespesasTotais();

    Map<String, Double> getLucroPorMes();

    Map<String, Double> getVendasPorTecnico();

    Map<String, Double> getComissoesPorTecnico();

    Map<String, Long> getServicosMaisVendidos();

    ResumoDashboardDTO getResumoGeral();
}
