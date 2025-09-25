package br.com.gestpro.gestpro_backend.infra.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // Cria configuração CORS
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permite cookies/autenticação
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Frontend permitido
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization")); // Cabeçalhos aceitos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        config.setExposedHeaders(Arrays.asList("Authorization")); // Cabeçalhos expostos para o cliente

        // Aplica configuração para todas as rotas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
