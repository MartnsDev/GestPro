package br.com.gestpro.gestpro_backend.infra.security;

import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.domain.service.GoogleAuthService;
import br.com.gestpro.gestpro_backend.infra.filters.JwtAuthenticationFilter;
import br.com.gestpro.gestpro_backend.infra.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurity {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final GoogleAuthService googleAuthService;

    public SpringSecurity(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomOAuth2UserService customOAuth2UserService,
                          UsuarioRepository usuarioRepository,
                          JwtService jwtService,
                          GoogleAuthService googleAuthService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.googleAuthService = googleAuthService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api-docs/**", "/swagger-ui/**", "/h2-console/**").permitAll()
                        .requestMatchers("/auth/**", "/oauth2/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuario").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // OAuth2 login Google
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            var oAuth2User = (org.springframework.security.oauth2.core.user.OAuth2User) authentication.getPrincipal();
                            String email = oAuth2User.getAttribute("email");
                            String nome = oAuth2User.getAttribute("name");
                            String foto = oAuth2User.getAttribute("picture");

                            // Cria ou atualiza usuário
                            Usuario usuario = googleAuthService.loginOrRegister(email, nome, foto);

                            // Gera token JWT
                            String token = googleAuthService.gerarToken(usuario);

                            // Salva o token no cookie HTTP-only (mais seguro) ou sessionStorage via frontend
                            // Aqui só redireciona para dashboard sem token na URL
                            response.sendRedirect("http://127.0.0.1:5500/dashboard.html");
                        })
                        .failureUrl("/auth/google/failure")
                );

        // Adiciona filtro JWT
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
