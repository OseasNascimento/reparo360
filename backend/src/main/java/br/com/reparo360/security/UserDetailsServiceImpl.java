package br.com.reparo360.security;

import br.com.reparo360.entity.Tecnico;
import br.com.reparo360.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TecnicoRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Tecnico tec = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Técnico não encontrado: " + email));

        // Converte roles do banco para GrantedAuthority
        List<GrantedAuthority> authorities = tec.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNomeRole()))
                .collect(Collectors.toList());

        return User.builder()
                .username(tec.getEmail())
                .password(tec.getSenha())       // hash BCrypt armazenado no BD
                .authorities(authorities)       // usa as roles reais do Técnico
                .build();
    }
}
