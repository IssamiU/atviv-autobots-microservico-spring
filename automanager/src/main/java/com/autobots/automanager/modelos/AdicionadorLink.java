package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public abstract class AdicionadorLink<T> {
    
    public abstract void adicionarLink(List<T> lista);
    public abstract void adicionarLink(T objeto);
    
    protected Link criarLink(String uri, String rel) {
        return Link.of(uri, rel);
    }
    
    protected String criarUri(Class<?> controllerClass, String methodName, Object... params) {
        return WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(controllerClass).getClass()
        ).toUri().toString();
    }
}