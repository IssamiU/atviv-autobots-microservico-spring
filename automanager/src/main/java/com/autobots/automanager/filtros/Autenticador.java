package com.autobots.automanager.filtros;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.jwt.ProvedorJwt;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Autenticador extends UsernamePasswordAuthenticationFilter {
    
    private AuthenticationManager authenticationManager;
    private ProvedorJwt provedorJwt;

    public Autenticador(AuthenticationManager authenticationManager, ProvedorJwt provedorJwt) {
        this.authenticationManager = authenticationManager;
        this.provedorJwt = provedorJwt;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            CredencialUsuarioSenha credencial = new ObjectMapper()
                    .readValue(request.getInputStream(), CredencialUsuarioSenha.class);
            
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credencial.getNomeUsuario(),
                            credencial.getSenha(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuário", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        
        String nomeUsuario = authentication.getName();
        String token = provedorJwt.proverJwt(nomeUsuario);
        
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
    }
}