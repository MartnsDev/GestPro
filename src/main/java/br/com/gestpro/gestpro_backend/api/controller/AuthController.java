package br.com.gestpro.gestpro_backend.api.controller;

import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.CadastrarUsuarioDTO;
import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.LoginUsuarioDTO;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.domain.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationService authService, UsuarioRepository usuarioRepository) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUsuarioDTO dto) {
        String token = authService.login(dto, "/auth/login");
        return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarUsuarioDTO dto) {
        String token = authService.cadastrar(dto, "/auth/cadastro");
        return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
    }
}
