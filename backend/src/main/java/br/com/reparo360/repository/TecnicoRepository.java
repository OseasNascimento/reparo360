package br.com.reparo360.repository;

import br.com.reparo360.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    Optional<Tecnico> findByEmail(String email);
    boolean existsByEmail(String email);
}
