package com.autobots.automanager.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AnalisadorJwt {
    private String assinatura;

    public AnalisadorJwt(String assinatura) {
        this.assinatura = assinatura;
    }

    public String obterNomeUsuario(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(assinatura.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }
}