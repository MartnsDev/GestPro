package br.com.gestpro.gestpro_backend.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    /**
     * Configurações de segurança da API
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF para APIs REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Não usa sessão
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api-docs/**", "/swagger-ui/**", "/h2-console/**").permitAll() // Permite acesso à documentação e H2
                        .anyRequest().authenticated() // Todas as outras rotas exigem autenticação
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // Para permitir console H2 em iframe

        return http.build();
    }

    /**
     * Encoder de senha
     * Para salvar senhas criptografadas no banco e comparar login
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
