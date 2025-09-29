package br.com.reparo360.financeiro.repository;

import br.com.reparo360.financeiro.entity.CategoriaFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositório JPA para CategoriaFinanceira.
 */
public interface CategoriaFinanceiraRepository extends JpaRepository<CategoriaFinanceira, Long> {
    Optional<CategoriaFinanceira> findByNomeIgnoreCase(String nome);
}
