package br.com.reparo360.repository;

import br.com.reparo360.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Adicione isto:
    Optional<Role> findByNomeRole(String nomeRole);
}
