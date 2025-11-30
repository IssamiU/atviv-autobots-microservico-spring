package com.autobots.automanager.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ProvedorJwt {

    @Value("${jwt.secret}")
    private String assinatura;
    
    @Value("${jwt.expiration}")
    private long duracao;

    public String proverJwt(String nomeUsuario) {
        GeradorJwt gerador = new GeradorJwt(assinatura, duracao);
        return gerador.gerarJwt(nomeUsuario);
    }

    public String gerar(Authentication authentication) {
        GeradorJwt gerador = new GeradorJwt(assinatura, duracao);
        String nomeUsuario = authentication.getName();
        return gerador.gerarJwt(nomeUsuario);
    }

    public boolean validarJwt(String jwt) {
        ValidadorJwt validador = new ValidadorJwt(assinatura);
        return validador.validar(jwt);
    }

    public String obterNomeUsuario(String jwt) {
        AnalisadorJwt analisador = new AnalisadorJwt(assinatura);
        return analisador.obterNomeUsuario(jwt);
    }
    
    private class GeradorJwt {
        private String assinatura;
        private long duracao;

        public GeradorJwt(String assinatura, long duracao) {
            this.assinatura = assinatura;
            this.duracao = duracao;
        }

        public String gerarJwt(String nomeUsuario) {
            java.util.Date expiracao = new java.util.Date(System.currentTimeMillis() + duracao);
            
            String jwt = io.jsonwebtoken.Jwts.builder()
                    .setSubject(nomeUsuario)
                    .setExpiration(expiracao)
                    .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, assinatura.getBytes())
                    .compact();
            return jwt;
        }
    }
}