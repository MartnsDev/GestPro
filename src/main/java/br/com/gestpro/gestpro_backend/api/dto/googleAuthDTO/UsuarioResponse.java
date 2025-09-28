package br.com.gestpro.gestpro_backend.api.dto.googleAuthDTO;

import br.com.gestpro.gestpro_backend.domain.model.enums.TipoPlano;
import lombok.Data;

@Data
public class UsuarioResponse {
    private String nome;
    private String email;
    private String foto;
    private TipoPlano tipoPlano; // agora é enum
    private String token; // JWT

    public UsuarioResponse(String nome, String email, String foto, TipoPlano tipoPlano, String token) {
        this.nome = nome;
        this.email = email;
        this.foto = foto;
        this.tipoPlano = tipoPlano;
        this.token = token;
    }

}