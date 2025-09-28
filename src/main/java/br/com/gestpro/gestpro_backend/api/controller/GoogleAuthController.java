package br.com.gestpro.gestpro_backend.api.controller;

import br.com.gestpro.gestpro_backend.api.dto.googleAuthDTO.UsuarioResponse;
import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.service.GoogleAuthService;
import br.com.gestpro.gestpro_backend.infra.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/google")
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @GetMapping("/success")
    public ResponseEntity<?> success(OAuth2AuthenticationToken authentication) {
        var user = authentication.getPrincipal();
        String email = user.getAttribute("email");
        String nome = user.getAttribute("name");
        String foto = user.getAttribute("picture");

        if (email == null) {
            throw new ApiException("Email não retornado pelo Google",
                    HttpStatus.NOT_FOUND,
                    "/auth/google/success");
        }

        try {
            Usuario usuario = googleAuthService.loginOrRegister(email, nome, foto);
            String token = googleAuthService.gerarToken(usuario);

            UsuarioResponse response = new UsuarioResponse(
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getFoto(),
                    usuario.getTipoPlano(),
                    token
            );

            return ResponseEntity.ok(response);

        } catch (ApiException ex) {
            return ResponseEntity
                    .status(ex.getStatus())
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno no login com Google"));
        }
    }
}
