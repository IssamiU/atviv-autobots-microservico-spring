package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Documento;

@Component
public class AdicionadorLinkDocumento extends AdicionadorLink<Documento> {

    @Override
    public void adicionarLink(List<Documento> lista) {
    }

    @Override
    public void adicionarLink(Documento documento) {
    }
}