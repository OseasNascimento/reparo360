package br.com.reparo360.financeiro.repository;

import br.com.reparo360.financeiro.entity.ApuracaoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório JPA para ApuracaoServico.
 */
public interface ApuracaoServicoRepository extends JpaRepository<ApuracaoServico, Long> {
    List<ApuracaoServico> findByOrdemServicoId(Long ordemServicoId);
}
