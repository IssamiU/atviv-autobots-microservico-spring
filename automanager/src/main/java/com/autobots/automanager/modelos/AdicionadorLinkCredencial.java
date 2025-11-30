package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialControle;
import com.autobots.automanager.entidades.Credencial;

@Component
public class AdicionadorLinkCredencial extends AdicionadorLink<Credencial> {

    @Override
    public void adicionarLink(List<Credencial> lista) {
        for (Credencial credencial : lista) {
            long id = credencial.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(CredencialControle.class)
                            .obterCredencial(id))
                    .withSelfRel();
            credencial.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Credencial credencial) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialControle.class)
                        .obterCredenciais())
                .withRel("credenciais");
        credencial.add(linkProprio);
    }
}