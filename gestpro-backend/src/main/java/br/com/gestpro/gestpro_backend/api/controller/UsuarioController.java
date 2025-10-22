package br.com.gestpro.gestpro_backend.api.controller;

import br.com.gestpro.gestpro_backend.api.dto.googleAuthDTO.UsuarioResponse;
import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.infra.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> getUsuario(@CookieValue(name="jwt_token", required=false) String token) {
        if (token == null || !jwtService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não logado ou token inválido");
        }

        String email = jwtService.getEmailFromToken(token);
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado");
        }

        UsuarioResponse response = new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getFoto(),
                usuario.getStatusAcesso() // token = null
        );

        return ResponseEntity.ok(response);
    }
}
