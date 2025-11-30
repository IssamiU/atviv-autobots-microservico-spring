package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;

@Component
public class AdicionadorLinkCredencialUsuarioSenha extends AdicionadorLink<CredencialUsuarioSenha> {

    @Override
    public void adicionarLink(List<CredencialUsuarioSenha> lista) {
    }

    @Override
    public void adicionarLink(CredencialUsuarioSenha credencial) {
    }
}