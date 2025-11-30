package com.autobots.automanager.jwt;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class GeradorJwt {
    
    @Value("${jwt.secret}")
    private String assinatura;
    
    @Value("${jwt.expiration}")
    private long duracao;

    public String gerar(Authentication authentication) {
        String nomeUsuario = authentication.getName();
        Date expiracao = new Date(System.currentTimeMillis() + duracao);
        
        String jwt = Jwts.builder()
                .setSubject(nomeUsuario)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS512, assinatura.getBytes())
                .compact();
        return jwt;
    }
}