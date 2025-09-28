package br.com.gestpro.gestpro_backend.api.controller;

import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.infra.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> getUsuario() {
        Usuario usuario = usuarioRepository.findAll().stream().findFirst().orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(404).body("Nenhum usuário encontrado");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("nome", usuario.getNome());
        claims.put("email", usuario.getEmail());
        claims.put("foto", usuario.getFoto());

        String token = jwtService.gerarToken(usuario, claims);

        Map<String, Object> response = new HashMap<>();
        response.put("nome", usuario.getNome());
        response.put("email", usuario.getEmail());
        response.put("foto", usuario.getFoto());
        response.put("tipoPlano", usuario.getTipoPlano());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }


}
