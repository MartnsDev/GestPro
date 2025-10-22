package br.com.gestpro.gestpro_backend.domain.service;

import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.LoginResponse;
import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.LoginUsuarioDTO;
import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.model.enums.StatusAcesso;
import br.com.gestpro.gestpro_backend.domain.model.enums.TipoPlano;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.infra.exceptions.ApiException;
import br.com.gestpro.gestpro_backend.infra.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.security.Key;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UsuarioRepository usuarioRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public Usuario cadastrar(@NotBlank String nome,
                             @Email String email,
                             @NotBlank String senha,
                             MultipartFile foto,
                             String path) throws IOException {

        if (usuarioRepository.existsByEmail(email)) {
            throw new ApiException("Email já cadastrado", HttpStatus.BAD_REQUEST, path);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setStatusAcesso(StatusAcesso.ATIVO);
        usuario.setTipoPlano(TipoPlano.EXPERIMENTAL);
        usuario.setDataPrimeiroLogin(LocalDateTime.now());

        if (foto != null && !foto.isEmpty()) {
            String caminho = salvarFoto(foto);
            usuario.setFotoUpload(caminho);
        }

        return usuarioRepository.save(usuario);
    }

    private String salvarFoto(MultipartFile foto) throws IOException {
        String nomeArquivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
        Path caminho = Paths.get("uploads/" + nomeArquivo);
        Files.createDirectories(caminho.getParent());
        Files.write(caminho, foto.getBytes());
        return caminho.toString();
    }

    @Transactional
    public LoginResponse login(LoginUsuarioDTO loginRequest, String path) {

        // 1. Busca usuário por email
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ApiException("Usuário não encontrado", HttpStatus.NOT_FOUND, path));

        // 2. Verifica se email foi confirmado
        if (!usuario.isEmailConfirmado()) {
            throw new ApiException("Email não confirmado", HttpStatus.BAD_REQUEST, path);
        }

        // 3. Verifica senha
        if (!passwordEncoder.matches(loginRequest.senha(), usuario.getSenha())) {
            throw new ApiException("Senha inválida", HttpStatus.UNAUTHORIZED, path);
        }

        // 4. Verifica plano EXPERIMENTAL
        if (usuario.getTipoPlano() == TipoPlano.EXPERIMENTAL) {
            if (usuario.getDataPrimeiroLogin() == null) {
                usuario.setDataPrimeiroLogin(LocalDateTime.now());
                usuarioRepository.save(usuario);
            } else {
                LocalDateTime expiracao = usuario.getDataPrimeiroLogin().plusDays(7);
                if (LocalDateTime.now().isAfter(expiracao)) {
                    usuario.setStatusAcesso(StatusAcesso.INATIVO);
                    usuarioRepository.save(usuario);
                    throw new ApiException(
                            "Seu período experimental de 7 dias expirou. É necessário pagamento para continuar.",
                            HttpStatus.FORBIDDEN,
                            path
                    );
                }
            }
        }

        // 5. Verifica status de acesso
        if (usuario.getStatusAcesso() == StatusAcesso.INATIVO) {
            throw new ApiException("Acesso inativo, necessário pagamento", HttpStatus.FORBIDDEN, path);
        }

        // 6. Gera token JWT
        String token = jwtService.gerarToken(usuario);

        // 7. Retorna dados do usuário + token
        return new LoginResponse(
                token,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipoPlano(),
                usuario.getFoto()
        );
    }

}
