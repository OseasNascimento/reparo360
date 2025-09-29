package br.com.reparo360.estoque.repository;

import br.com.reparo360.estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositório JPA para CRUD de Produto.
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNome(String nome);
}
