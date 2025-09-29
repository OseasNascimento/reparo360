package br.com.reparo360.dashboard;


import br.com.reparo360.dashboard.dto.MediaAvaliacoesDTO;
import br.com.reparo360.dashboard.dto.ResumoDashboardDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Tag(name = "Dashboard", description = "Métricas e relatórios gerais do sistema")
@RequestMapping("/api/dashboard")
public interface DashboardApi {

    @Operation(summary = "Total de serviços cadastrados")
    @ApiResponse(responseCode = "200", description = "Quantidade total de serviços",
            content = @Content(schema = @Schema(type = "integer", example = "42")))
    @GetMapping("/servicos/total")
    ResponseEntity<Long> totalServicos();

    @Operation(summary = "Serviços concluídos")
    @ApiResponse(responseCode = "200", description = "Número de serviços concluídos",
            content = @Content(schema = @Schema(type = "integer", example = "30")))
    @GetMapping("/servicos/concluidos")
    ResponseEntity<Long> servicosConcluidos();

    @Operation(summary = "Serviços cancelados")
    @ApiResponse(responseCode = "200", description = "Número de serviços cancelados",
            content = @Content(schema = @Schema(type = "integer", example = "5")))
    @GetMapping("/servicos/cancelados")
    ResponseEntity<Long> servicosCancelados();

    @Operation(summary = "Serviços finalizados")
    @ApiResponse(responseCode = "200", description = "Número de serviços finalizados",
            content = @Content(schema = @Schema(type = "integer", example = "37")))
    @GetMapping("/servicos/finalizados")
    ResponseEntity<Long> servicosFinalizados();

    @Operation(summary = "Clientes únicos ativos")
    @ApiResponse(responseCode = "200", description = "Quantidade de clientes ativos",
            content = @Content(schema = @Schema(type = "integer", example = "100")))
    @GetMapping("/clientes/ativos")
    ResponseEntity<Long> clientesUnicos();

    @Operation(summary = "Distribuição de clientes por origem")
    @ApiResponse(responseCode = "200", description = "Mapa de origem → quantidade de clientes",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/clientes/por-origem")
    ResponseEntity<Map<String, Long>> clientesPorOrigem();

    @Operation(summary = "Ordens de serviço por status")
    @ApiResponse(responseCode = "200", description = "Mapa status → quantidade de OS",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/os/por-status")
    ResponseEntity<Map<String, Long>> osPorStatus();

    @Operation(summary = "Ordens de serviço por serviço")
    @ApiResponse(responseCode = "200", description = "Mapa serviço → quantidade de OS",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/os/por-servico")
    ResponseEntity<Map<String, Long>> osPorServico();

    @Operation(summary = "Valor total das ordens de serviço")
    @ApiResponse(responseCode = "200", description = "Soma dos valores de todas as OS",
            content = @Content(schema = @Schema(type = "number", format = "double", example = "12500.00")))
    @GetMapping("/os/valor-total")
    ResponseEntity<Double> valorTotalOS();

    @Operation(summary = "Custo total das ordens de serviço")
    @ApiResponse(responseCode = "200", description = "Soma dos custos de todas as OS",
            content = @Content(schema = @Schema(type = "number", format = "double", example = "8000.00")))
    @GetMapping("/os/custo-total")
    ResponseEntity<Double> custoTotalOS();

    @Operation(summary = "Receita total no financeiro")
    @ApiResponse(responseCode = "200", description = "Receita financeira total",
            content = @Content(schema = @Schema(type = "number", format = "double", example = "15000.00")))
    @GetMapping("/financeiro/receita")
    ResponseEntity<Double> receitaTotal();

    @Operation(summary = "Despesas totais no financeiro")
    @ApiResponse(responseCode = "200", description = "Despesa financeira total",
            content = @Content(schema = @Schema(type = "number", format = "double", example = "7000.00")))
    @GetMapping("/financeiro/despesas")
    ResponseEntity<Double> despesasTotais();

    @Operation(summary = "Lucro por mês")
    @ApiResponse(responseCode = "200", description = "Mapa mês → lucro",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/financeiro/lucro-por-mes")
    ResponseEntity<Map<String, Double>> lucroPorMes();

    @Operation(summary = "Vendas por técnico")
    @ApiResponse(responseCode = "200", description = "Mapa técnico → total de vendas",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/tecnicos/vendas")
    ResponseEntity<Map<String, Double>> vendasPorTecnico();

    @Operation(summary = "Comissões por técnico")
    @ApiResponse(responseCode = "200", description = "Mapa técnico → comissão total",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/tecnicos/comissoes")
    ResponseEntity<Map<String, Double>> comissoesPorTecnico();

    @Operation(summary = "Serviços mais vendidos")
    @ApiResponse(responseCode = "200", description = "Mapa serviço → quantidade vendida",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/servicos/mais-vendidos")
    ResponseEntity<Map<String, Long>> servicosMaisVendidos();

    @Operation(summary = "Resumo geral do dashboard")
    @ApiResponse(responseCode = "200", description = "Indicadores principais do dashboard",
            content = @Content(schema = @Schema(implementation = ResumoDashboardDTO.class)))
    @GetMapping("/resumo")
    ResponseEntity<ResumoDashboardDTO> resumo();
}
