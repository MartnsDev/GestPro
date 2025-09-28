package br.com.gestpro.gestpro_backend.domain.service;

import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.model.enums.StatusAcesso;
import br.com.gestpro.gestpro_backend.domain.model.enums.TipoPlano;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.infra.jwt.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public GoogleAuthService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public Usuario loginOrRegister(String email, String nome, String foto) {
        return usuarioRepository.findByEmail(email)


                .map(u -> {
                    // Atualiza nome e foto sempre
                    u.setNome(nome);
                    u.setFoto(foto);

                    // Primeiro login
                    if (u.getDataPrimeiroLogin() == null) {
                        u.setDataPrimeiroLogin(LocalDateTime.now());
                    }

                    // Verifica 7 dias de acesso gratuito
                    if (u.getTipoPlano() == TipoPlano.EXPERIMENTAL &&
                            u.getDataPrimeiroLogin().plusDays(7).isBefore(LocalDateTime.now())) {
                        u.setStatusAcesso(StatusAcesso.INATIVO);
                    } else {
                        u.setStatusAcesso(StatusAcesso.ATIVO);
                    }

                    return usuarioRepository.save(u);
                })
                .orElseGet(() -> {
                    // Cria novo usuário
                    Usuario novoUsuario = new Usuario();
                    novoUsuario.setNome(nome);
                    novoUsuario.setEmail(email);
                    novoUsuario.setSenha(null);
                    novoUsuario.setFoto(foto);
                    novoUsuario.setTipoPlano(TipoPlano.EXPERIMENTAL); // padrão inicial

                    LocalDateTime agora = LocalDateTime.now();
                    novoUsuario.setDataCriacao(agora);
                    novoUsuario.setDataPrimeiroLogin(agora);
                    novoUsuario.setStatusAcesso(StatusAcesso.ATIVO);

                    return usuarioRepository.save(novoUsuario);
                });
    }

    public String gerarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nome", usuario.getNome());
        claims.put("email", usuario.getEmail());
        claims.put("foto", usuario.getFoto()); // <<< adicione isso
        return jwtService.gerarToken(usuario, claims);
    }

}
