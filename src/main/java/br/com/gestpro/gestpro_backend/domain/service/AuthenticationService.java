package br.com.gestpro.gestpro_backend.domain.service;

import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.CadastrarUsuarioDTO;
import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.LoginUsuarioDTO;
import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.model.enums.TipoPlano;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.infra.exceptions.ApiException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public AuthenticationService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // --- Login clássico ---
    public String login(LoginUsuarioDTO dto, String path) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(dto.email());

        if (usuarioOpt.isEmpty()) {
            throw new ApiException("Usuário não encontrado", HttpStatus.NOT_FOUND, path);
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new ApiException("Senha inválida", HttpStatus.UNAUTHORIZED, path);
        }

        return gerarToken(usuario.getEmail());
    }

    // --- Cadastro ---
    public String cadastrar(CadastrarUsuarioDTO dto, String path) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new ApiException("E-mail já cadastrado", HttpStatus.CONFLICT, path);
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setSenha(passwordEncoder.encode(dto.senha()));
        novoUsuario.setFoto("https://www.gravatar.com/avatar/default");
        novoUsuario.getTipoPlano(); // plano inicial gratuito

        usuarioRepository.save(novoUsuario);

        return gerarToken(novoUsuario.getEmail());
    }

    // --- Geração de JWT ---
    private String gerarToken(String email) {
        long validade = 1000 * 60 * 60 * 24; // 24h
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validade))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
