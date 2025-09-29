package br.com.reparo360.security;

import br.com.reparo360.entity.Role;
import br.com.reparo360.entity.Tecnico;
import br.com.reparo360.repository.RoleRepository;
import br.com.reparo360.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class BootstrapAdminConfig {

    private final TecnicoRepository tecnicoRepo;
    private final RoleRepository    roleRepo;
    private final PasswordEncoder   encoder;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {
            // 1) cria ou recupera ROLE_ADMIN
            Role adminRole = roleRepo.findByNomeRole("ROLE_ADMIN")
                    .orElseGet(() -> roleRepo.save(
                            Role.builder()
                                    .nomeRole("ROLE_ADMIN")
                                    .descricao("Perfil de Administrador")
                                    .build()
                    ));

            // 2) cria ou recupera ROLE_TECNICO
            Role tecnicoRole = roleRepo.findByNomeRole("ROLE_TECNICO")
                    .orElseGet(() -> roleRepo.save(
                            Role.builder()
                                    .nomeRole("ROLE_TECNICO")
                                    .descricao("Perfil de Técnico")
                                    .build()
                    ));

            // 3) cria o usuário admin, já com as duas roles
            String emailAdmin = "admin@reparo360.com";
            if (tecnicoRepo.findByEmail(emailAdmin).isEmpty()) {
                Tecnico admin = Tecnico.builder()
                        .nome("Administrador do Sistema")
                        .email(emailAdmin)
                        .senha(encoder.encode("Admin@123"))
                        .telefone("(11) 99999-0000")
                        .especialidade("Gestão")
                        .dataContratacao(LocalDateTime.now())
                        .roles(Set.of(adminRole, tecnicoRole))
                        .build();

                tecnicoRepo.save(admin);
                System.out.println(">>> ADMIN criado: " + emailAdmin + " / Admin@123");
            } else {
                System.out.println(">>> ADMIN já existe, nada a fazer");
            }
        };
    }
}
