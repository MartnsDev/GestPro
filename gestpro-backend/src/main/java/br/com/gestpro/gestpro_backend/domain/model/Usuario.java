package br.com.gestpro.gestpro_backend.domain.model;

import br.com.gestpro.gestpro_backend.domain.model.enums.StatusAcesso;
import br.com.gestpro.gestpro_backend.domain.model.enums.TipoPlano;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    @Column(name = "foto_google")
    private String foto; // Google Login

    @Column
    private String fotoUpload; // caminho do upload da foto

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_plano", nullable = false)
    private TipoPlano tipoPlano = TipoPlano.EXPERIMENTAL;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_primeiro_login")
    private LocalDateTime dataPrimeiroLogin;

    @Column(name = "data_assinatura_plus")
    private LocalDateTime dataAssinaturaPlus; // <- novo campo

    @Enumerated(EnumType.STRING)
    @Column(name = "status_acesso", nullable = false)
    private StatusAcesso statusAcesso = StatusAcesso.ATIVO;

    private String codigoRecuperacao;
    @Column(nullable = false)

    private boolean emailConfirmado = false;

    @Column
    private String tokenConfirmacao;

    @Column
    private LocalDateTime dataEnvioConfirmacao;


    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (dataPrimeiroLogin == null) {
            dataPrimeiroLogin = LocalDateTime.now();
        }
        if (statusAcesso == null) {
            statusAcesso = StatusAcesso.ATIVO;
        }
        if (tipoPlano == null) {
            tipoPlano = TipoPlano.EXPERIMENTAL;
        }
        // Se já estiver como PLUS, define a data de assinatura
        if (tipoPlano == TipoPlano.ASSINANTE && dataAssinaturaPlus == null) {
            dataAssinaturaPlus = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        // Se o tipoPlano mudar para PLUS e a data não estiver definida, define a data atual
        if (tipoPlano == TipoPlano.ASSINANTE && dataAssinaturaPlus == null) {
            dataAssinaturaPlus = LocalDateTime.now();
        }
    }
}
