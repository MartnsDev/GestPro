package br.com.gestpro.gestpro_backend.infra.filters;

import br.com.gestpro.gestpro_backend.domain.service.GoogleAuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final GoogleAuthService googleAuthService;

    public OAuth2LoginSuccessHandler(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        var attributes = authToken.getPrincipal().getAttributes();

        String email = attributes.get("email").toString();
        String nome = attributes.get("name").toString();
        String foto = attributes.get("picture").toString();

        // Atualiza ou cadastra usuário no banco
        googleAuthService.loginOrRegister(email, nome, foto);

        // Redireciona apenas para o frontend, sem token
        response.sendRedirect("http://127.0.0.1:5500/dashboard.html");
    }
}
