package br.com.reparo360.financeiro.repository;

import br.com.reparo360.financeiro.entity.LancamentoFinanceiro;
import br.com.reparo360.financeiro.entity.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório JPA para LancamentoFinanceiro.
 */
public interface LancamentoFinanceiroRepository extends JpaRepository<LancamentoFinanceiro, Long> {
    List<LancamentoFinanceiro> findByContaCaixaId(Long contaCaixaId);
    List<LancamentoFinanceiro> findByTipoTransacao(TipoTransacao tipoTransacao);

}
