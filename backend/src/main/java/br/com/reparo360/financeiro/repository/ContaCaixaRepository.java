package br.com.reparo360.financeiro.repository;

import br.com.reparo360.financeiro.entity.ContaCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repositório JPA para ContaCaixa.
 */
public interface ContaCaixaRepository extends JpaRepository<ContaCaixa, Long> {
    Optional<ContaCaixa> findByNome(String nome);

    @Query("SELECT COALESCE(SUM(l.valor),0) FROM LancamentoFinanceiro l WHERE l.tipoTransacao = 'DESPESA'")
    Double sumDespesas();




}
