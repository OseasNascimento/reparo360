package br.com.reparo360.config.repository;

import br.com.reparo360.config.entity.ParametroConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParametroConfigRepository extends JpaRepository<ParametroConfig, Long> {
    Optional<ParametroConfig> findByChave(String chave);
}
