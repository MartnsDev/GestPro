package br.com.gestpro.gestpro_backend.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Chave secreta do JWT, que é lida do application.properties.
    // Em produção, nunca coloque hardcoded no código.
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // ===============================
    // EXTRAÇÃO DE INFORMAÇÕES DO TOKEN
    // ===============================

    // Extrai o "username" (subject) do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrai qualquer "claim" do token usando uma função
    // Por exemplo, pode extrair o expiration ou qualquer outro dado
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ===============================
    // GERAÇÃO DE TOKENS
    // ===============================

    // Gera token apenas com os dados básicos do usuário
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Gera token com claims extras (informações adicionais)
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)                  // adiciona claims extras
                .setSubject(userDetails.getUsername())   // define o username como subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // data de criação
                // 3 dias de validade
                .setExpiration(new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000)) // expira em 3 dias
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // assina com HMAC SHA-256
                .compact();                               // gera a string final do token
    }

    // ===============================
    // VALIDAÇÃO DE TOKENS
    // ===============================

    // Verifica se o token é válido (username bate e não expirou)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Checa se o token expirou
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extrai a data de expiração do token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ===============================
    // FUNÇÕES INTERNAS
    // ===============================

    // Extrai todas as claims do token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // usa a chave para validar a assinatura
                .build()
                .parseClaimsJws(token)       // faz o parse do token
                .getBody();                  // retorna o corpo com as claims
    }

    // Cria a chave de assinatura a partir da SECRET_KEY
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
