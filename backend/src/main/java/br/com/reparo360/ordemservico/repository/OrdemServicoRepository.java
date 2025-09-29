package br.com.reparo360.ordemservico.repository;

import br.com.reparo360.ordemservico.entity.OrdemServico;
import br.com.reparo360.ordemservico.entity.StatusOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    // CRUD/consultas básicas
    List<OrdemServico> findByStatus(StatusOrdemServico status);
    Long countByStatus(StatusOrdemServico status);

    // ==== BUSCAS POR AGENDAMENTO (para o front evitar duplicidades) ====
    Optional<OrdemServico> findFirstByAgendamentoIdAgendamento(Long agendamentoId);
    boolean existsByAgendamentoIdAgendamento(Long agendamentoId);

    @Query("select o.id from OrdemServico o where o.agendamento.idAgendamento = :agendamentoId")
    Optional<Long> findIdByAgendamentoId(@Param("agendamentoId") Long agendamentoId);

    // 1) Média por dia (por data_criacao)
    @Query(value = """
        SELECT AVG(cnt)
        FROM (
          SELECT COUNT(*) AS cnt
            FROM ordens_servico
           GROUP BY CAST(data_criacao AS DATE)
        ) AS sub
        """, nativeQuery = true)
    Double mediaServicosPorDia();

    // 2) Clientes distintos
    @Query("SELECT COUNT(DISTINCT o.agendamento.cliente.id) FROM OrdemServico o")
    Long countDistinctClientes();

    // 3) Contagem por status
    @Query("SELECT o.status, COUNT(o) FROM OrdemServico o GROUP BY o.status")
    List<Object[]> countGroupByStatus();

    // 4) Contagem por descrição de serviço
    @Query("""
      SELECT s.descricao, COUNT(o)
        FROM OrdemServico o
        JOIN o.agendamento a
        JOIN a.servicos s
       GROUP BY s.descricao
    """)
    List<Object[]> countGroupByServico();

    // 5) Somatórios (filtrando por status)
    @Query("""
      SELECT SUM(o.valorServico)
        FROM OrdemServico o
       WHERE o.status = :status
    """)
    Double sumValorTotal(@Param("status") StatusOrdemServico status);

    @Query("""
      SELECT SUM(o.valorMateriais)
        FROM OrdemServico o
       WHERE o.status = :status
    """)
    Double sumCustoTotal(@Param("status") StatusOrdemServico status);

    // 6) Lucro por mês — lendo a apuração oficial
    @Query(value = """
        SELECT
          YEAR(a.data_apuracao)  AS ano,
          MONTH(a.data_apuracao) AS mes,
          SUM(a.receita_liquida) AS lucro
          FROM apuracoes_servico a
        GROUP BY YEAR(a.data_apuracao), MONTH(a.data_apuracao)
        ORDER BY ano, mes
        """, nativeQuery = true)
    List<Object[]> getLucroPorMes();

    // 7) Vendas por técnico (valor_servico das OS assinadas)
    @Query(value = """
        SELECT t.nome               AS tecnico,
               SUM(o.valor_servico) AS total
          FROM ordens_servico o
          JOIN agendamentos a ON o.agendamento_id = a.id_agendamento
          JOIN tecnicos t     ON a.tecnico_id     = t.tecnico_id
         WHERE o.status = 'ASSINADA'
         GROUP BY t.nome
        """, nativeQuery = true)
    List<Object[]> getVendasPorTecnico();

    // 8) Comissões por técnico (10% do valor do serviço)
    @Query(value = """
        SELECT t.nome                         AS tecnico,
               SUM(o.valor_servico * 0.1)     AS comissao
          FROM ordens_servico o
          JOIN agendamentos a ON o.agendamento_id = a.id_agendamento
          JOIN tecnicos t     ON a.tecnico_id     = t.tecnico_id
         WHERE o.status = 'ASSINADA'
         GROUP BY t.nome
        """, nativeQuery = true)
    List<Object[]> getComissoesPorTecnico();

    // 9) Serviços mais vendidos
    @Query("""
      SELECT s.descricao, COUNT(o)
        FROM OrdemServico o
        JOIN o.agendamento a
        JOIN a.servicos s
       GROUP BY s.descricao
       ORDER BY COUNT(o) DESC
    """)
    List<Object[]> getServicosMaisVendidos();
}
