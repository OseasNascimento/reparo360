package br.com.reparo360.dashboard.controller;

import br.com.reparo360.dashboard.DashboardApi;
import br.com.reparo360.dashboard.dto.ResumoDashboardDTO;
import br.com.reparo360.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController implements DashboardApi {

    private final DashboardService dashboardService;

    // DTO enxuto para a versão em lista (opcional)
    public record MesValorDTO(String iso, String mes, double valor) {}

    private static final String[] MESES_PT_CURTOS = {
            "jan","fev","mar","abr","mai","jun","jul","ago","set","out","nov","dez"
    };

    @GetMapping("/servicos/total")
    public ResponseEntity<Long> totalServicos() {
        return ResponseEntity.ok(dashboardService.getTotalServicos());
    }

    @GetMapping("/servicos/concluidos")
    public ResponseEntity<Long> servicosConcluidos() {
        return ResponseEntity.ok(dashboardService.getServicosConcluidos());
    }

    @GetMapping("/servicos/cancelados")
    public ResponseEntity<Long> servicosCancelados() {
        return ResponseEntity.ok(dashboardService.getServicosCancelados());
    }

    @GetMapping("/servicos/finalizados")
    public ResponseEntity<Long> servicosFinalizados() {
        return ResponseEntity.ok(dashboardService.getServicosFinalizados());
    }

    @GetMapping("/clientes/ativos")
    public ResponseEntity<Long> clientesUnicos() {
        return ResponseEntity.ok(dashboardService.getClientesAtivos());
    }

    @GetMapping("/clientes/por-origem")
    public ResponseEntity<Map<String, Long>> clientesPorOrigem() {
        return ResponseEntity.ok(dashboardService.getClientesPorOrigem());
    }

    @GetMapping("/os/por-status")
    public ResponseEntity<Map<String, Long>> osPorStatus() {
        return ResponseEntity.ok(dashboardService.getOrdensPorStatus());
    }

    @GetMapping("/os/por-servico")
    public ResponseEntity<Map<String, Long>> osPorServico() {
        return ResponseEntity.ok(dashboardService.getOrdensPorServico());
    }

    @GetMapping("/os/valor-total")
    public ResponseEntity<Double> valorTotalOS() {
        return ResponseEntity.ok(dashboardService.getValorTotalOrdens());
    }

    @GetMapping("/os/custo-total")
    public ResponseEntity<Double> custoTotalOS() {
        return ResponseEntity.ok(dashboardService.getCustoTotalOrdens());
    }

    @GetMapping("/financeiro/receita")
    public ResponseEntity<Double> receitaTotal() {
        return ResponseEntity.ok(dashboardService.getReceitaTotal());
    }

    @GetMapping("/financeiro/despesas")
    public ResponseEntity<Double> despesasTotais() {
        return ResponseEntity.ok(dashboardService.getDespesasTotais());
    }

    /**
     * Mantém a versão atual que retorna Map<YYYY-MM, valor>.
     * Compatível com seu front existente.
     */
    @GetMapping("/financeiro/lucro-por-mes")
    public ResponseEntity<Map<String, Double>> lucroPorMes() {
        return ResponseEntity.ok(dashboardService.getLucroPorMes());
    }

    /**
     * (Opcional) Nova rota que retorna SEMPRE 12 meses do ano atual,
     * com zeros quando não houver dado. Facilita o consumo no front.
     */
    @GetMapping("/financeiro/lucro-por-mes/lista")
    public ResponseEntity<List<MesValorDTO>> lucroPorMesLista() {
        Map<String, Double> mapa = dashboardService.getLucroPorMes();
        int ano = Year.now().getValue();

        List<MesValorDTO> out = new ArrayList<>(12);
        for (int m = 1; m <= 12; m++) {
            String iso = String.format("%04d-%02d", ano, m);
            double valor = Optional.ofNullable(mapa.get(iso)).orElse(0.0);
            out.add(new MesValorDTO(iso, MESES_PT_CURTOS[m - 1], valor));
        }
        return ResponseEntity.ok(out);
    }

    @GetMapping("/tecnicos/vendas")
    public ResponseEntity<Map<String, Double>> vendasPorTecnico() {
        return ResponseEntity.ok(dashboardService.getVendasPorTecnico());
    }

    @GetMapping("/tecnicos/comissoes")
    public ResponseEntity<Map<String, Double>> comissoesPorTecnico() {
        return ResponseEntity.ok(dashboardService.getComissoesPorTecnico());
    }

    @GetMapping("/servicos/mais-vendidos")
    public ResponseEntity<Map<String, Long>> servicosMaisVendidos() {
        return ResponseEntity.ok(dashboardService.getServicosMaisVendidos());
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoDashboardDTO> resumo() {
        return ResponseEntity.ok(dashboardService.getResumoGeral());
    }
}
