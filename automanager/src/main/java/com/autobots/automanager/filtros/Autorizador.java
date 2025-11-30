package com.autobots.automanager.filtros;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.autobots.automanager.jwt.ProvedorJwt;

public class Autorizador extends BasicAuthenticationFilter {
    
    private ProvedorJwt provedorJwt;
    private UserDetailsService userDetailsService;

    public Autorizador(AuthenticationManager authenticationManager, ProvedorJwt provedorJwt,
            UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.provedorJwt = provedorJwt;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        String header = request.getHeader("Authorization");
        
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        
        UsernamePasswordAuthenticationToken autenticacao = obterAutenticacao(request);
        SecurityContextHolder.getContext().setAuthentication(autenticacao);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken obterAutenticacao(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        
        if (token != null) {
            String jwt = token.replace("Bearer ", "");
            AutenticadorJwt autenticador = new AutenticadorJwt(jwt, provedorJwt, userDetailsService);
            return autenticador.obterAutenticacao();
        }
        return null;
    }
}