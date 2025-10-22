package br.com.gestpro.gestpro_backend.api.controller;

import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.CadastroRequestDTO;
import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.LoginResponse;
import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.LoginUsuarioDTO;
import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.domain.service.AuthenticationService;
import br.com.gestpro.gestpro_backend.domain.service.EmailService;
import br.com.gestpro.gestpro_backend.infra.exceptions.ApiException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.base-url}")
    private String baseUrl;


    public AuthController(
            AuthenticationService authService,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // ===============================
    // Cadastro de usuário
    // ===============================
    @PostMapping(value = "/cadastro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cadastrarUsuario(
            @Valid @ModelAttribute CadastroRequestDTO request,
            BindingResult bindingResult,
            HttpServletRequest httpRequest
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> erros = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            fe -> fe.getField(),
                            fe -> fe.getDefaultMessage()
                    ));
            return ResponseEntity.badRequest().body(erros);
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("erro", "E-mail já cadastrado."));
        }

        // Cria o usuário
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setFoto(request.getFoto() != null ? request.getFoto().getOriginalFilename() : null);
        usuario.setEmailConfirmado(false);
        usuario.setTokenConfirmacao(UUID.randomUUID().toString());
        usuario.setDataEnvioConfirmacao(LocalDateTime.now());

        usuarioRepository.save(usuario);

        // Monta o link de confirmação
        String linkConfirmacao = baseUrl + "/auth/confirmar?token=" + usuario.getTokenConfirmacao();

        // Envia o e-mail
        emailService.enviarConfirmacao(usuario.getEmail(), linkConfirmacao);

        return ResponseEntity.ok(Map.of("mensagem", "Cadastro realizado! Verifique seu e-mail para confirmar a conta."));
    }

    @GetMapping("/confirmar")
    public void confirmarEmail(@RequestParam String token, HttpServletResponse response) throws IOException {
        try {
            Usuario usuario = usuarioRepository.findByTokenConfirmacao(token)
                    .orElseThrow(() -> new ApiException("Token inválido ou expirado.", HttpStatus.BAD_REQUEST, "/confirmar"));

            usuario.setEmailConfirmado(true);
            usuario.setTokenConfirmacao(null);
            usuarioRepository.save(usuario);

            // Redireciona para o frontend com sucesso
            response.sendRedirect("http://localhost:3000/confirmar-email?status=sucesso");
        } catch (ApiException e) {
            // Redireciona para o frontend com erro
            response.sendRedirect("http://localhost:3000/confirmar-email?status=erro");
        }
    }

    // ===============================
    // Login
    // ===============================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUsuario(@RequestBody LoginUsuarioDTO loginRequest) {
        // Passa o path do endpoint para gerar mensagens de erro corretas
        LoginResponse loginResponse = authService.login(loginRequest, "/auth/login");

        // Cria cookie HttpOnly com token JWT
        ResponseCookie cookie = ResponseCookie.from("jwt_token", loginResponse.token())
                .httpOnly(true)
                .secure(false) // true se usar HTTPS
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        // Retorna dados do usuário sem token no body
        LoginResponse safeResponse = new LoginResponse(
                null,
                loginResponse.nome(),
                loginResponse.email(),
                loginResponse.tipoPlano(),
                loginResponse.foto()
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(safeResponse);
    }




}
