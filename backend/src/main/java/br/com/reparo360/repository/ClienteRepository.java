package br.com.reparo360.repository;

import br.com.reparo360.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmailIgnoreCase(String email);

    @Query("SELECT c.origem, COUNT(c) FROM Cliente c GROUP BY c.origem")
    List<Object[]> countByOrigem();

}