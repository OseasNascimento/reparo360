package br.com.reparo360.security;

import br.com.reparo360.entity.Tecnico;
import br.com.reparo360.repository.TecnicoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import br.com.reparo360.dto.LoginRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final TecnicoRepository tecnicoRepo;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          TecnicoRepository tecnicoRepo) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.tecnicoRepo = tecnicoRepo;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
            );
            String token = jwtUtil.generateToken(auth);

            Tecnico tec = tecnicoRepo.findByEmail(dto.email())
                    .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

            // extrai a primeira role (sem o prefixo ROLE_)
            String role = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("");
            role = role.replaceFirst("^ROLE_", "");

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "tecnicoId", tec.getId(),
                    "role", role
            ));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Credenciais inválidas"));
        }
    }
}

