package br.com.reparo360.estoque.repository;

import br.com.reparo360.estoque.entity.PecaUtilizada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório JPA para PecaUtilizada.
 */
public interface PecaUtilizadaRepository extends JpaRepository<PecaUtilizada, Long> {
    List<PecaUtilizada> findByOrdemServicoId(Long ordemServicoId);
}
