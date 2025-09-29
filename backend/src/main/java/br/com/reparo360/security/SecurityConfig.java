package br.com.reparo360.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter,
                          UserDetailsServiceImpl userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    /** 1) Ignora H2 console (DEV) */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**");
    }

    /** 2) Configura chain de filtros e autorização */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // ───── PUBLIC ─────────────────────────────────────────
                        .requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
                        .requestMatchers(HttpMethod.GET, "/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/tecnicos").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/tecnicos").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/servicos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clientes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/agendamentos").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/clientes/email/**").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/agendamentos/disponibilidade**").permitAll()

                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                        .permitAll()

                        // ───── PROTECTED ───────────────────────────────────────
                        // Lista e busca agendamentos → só TECNICO ou ADMIN
                        .requestMatchers(HttpMethod.GET,
                                "/api/agendamentos","/api/agendamentos/*").hasAnyRole("TECNICO", "ADMIN")

                        // Status de agendamento → só TECNICO
                        .requestMatchers(HttpMethod.PATCH, "/api/agendamentos/*/status").hasRole("TECNICO")

                        // CRUD de serviços → só ADMIN
                        .requestMatchers(HttpMethod.POST,   "/api/servicos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/servicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").hasRole("ADMIN")

                        // CRUD de técnicos → só ADMIN
                        .requestMatchers(HttpMethod.PUT,    "/api/tecnicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tecnicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/tecnicos/**").hasAnyRole("ADMIN","TECNICO")

                        // Outros endpoints (movimentações, estoque, dashboard etc.) → autenticado
                        .anyRequest().authenticated()
                )

                // Provider + JWT filter
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // Desabilita frameOptions (para H2 console apenas)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    /** 3) Expõe AuthenticationManager para o AuthController */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)
            throws Exception {
        return cfg.getAuthenticationManager();
    }

    /** 3.1) DaoAuthenticationProvider com nosso UserDetailsService + BCrypt */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }

    /** 3.2) BCrypt para checagem de senhas */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** 4) CORS: libera front em http://localhost:3000 */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:3000"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}
