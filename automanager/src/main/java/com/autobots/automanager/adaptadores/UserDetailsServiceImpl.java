package com.autobots.automanager.adaptadores;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuario.findAll().stream()
                .filter(u -> u.getCredenciais().stream()
                        .anyMatch(c -> c instanceof CredencialUsuarioSenha && 
                                     ((CredencialUsuarioSenha) c).getNomeUsuario().equals(username)))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        CredencialUsuarioSenha credencial = (CredencialUsuarioSenha) usuario.getCredenciais().stream()
                .filter(c -> c instanceof CredencialUsuarioSenha && 
                           ((CredencialUsuarioSenha) c).getNomeUsuario().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Credencial não encontrada"));

        return UserDetailsImpl.build(usuario, credencial);
    }
}