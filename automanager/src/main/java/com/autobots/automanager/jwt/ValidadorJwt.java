package com.autobots.automanager.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

public class ValidadorJwt {
    private String assinatura;

    public ValidadorJwt(String assinatura) {
        this.assinatura = assinatura;
    }

    public boolean validar(String jwt) {
        try {
            Jwts.parser().setSigningKey(assinatura.getBytes()).parseClaimsJws(jwt);
            return true;
        } catch (SignatureException e) {
            System.err.println("Assinatura JWT inválida: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Token JWT inválido: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Token JWT expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Token JWT não suportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string vazia: " + e.getMessage());
        }
        return false;
    }
}