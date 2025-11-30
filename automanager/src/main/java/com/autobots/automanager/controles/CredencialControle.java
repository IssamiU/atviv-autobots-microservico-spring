package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.modelos.AdicionadorLinkCredencial;
import com.autobots.automanager.repositorios.RepositorioCredencialCodigoBarra;
import com.autobots.automanager.repositorios.RepositorioCredencialUsuarioSenha;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {
    
    @Autowired
    private RepositorioCredencialCodigoBarra repositorioCodigoBarra;
    
    @Autowired
    private RepositorioCredencialUsuarioSenha repositorioUsuarioSenha;
    
    @Autowired
    private AdicionadorLinkCredencial adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Credencial> obterCredencial(@PathVariable Long id) {
        Credencial credencial = null;
        
        credencial = repositorioCodigoBarra.findAll().stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
        
        if (credencial == null) {
            credencial = repositorioUsuarioSenha.findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        }
        
        if (credencial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        adicionadorLink.adicionarLink(credencial);
        return new ResponseEntity<>(credencial, HttpStatus.OK);
    }

    @GetMapping("/credenciais")
    public ResponseEntity<List<Credencial>> obterCredenciais() {
        List<Credencial> credenciais = new java.util.ArrayList<>();
        credenciais.addAll(repositorioCodigoBarra.findAll());
        credenciais.addAll(repositorioUsuarioSenha.findAll());
        
        if (credenciais.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        adicionadorLink.adicionarLink(credenciais);
        return new ResponseEntity<>(credenciais, HttpStatus.OK);
    }
}