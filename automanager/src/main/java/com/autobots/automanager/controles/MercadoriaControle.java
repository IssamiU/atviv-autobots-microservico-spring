package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.AdicionadorLinkMercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {
    
    @Autowired
    private RepositorioMercadoria repositorio;
    
    @Autowired
    private AdicionadorLinkMercadoria adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long id) {
        List<Mercadoria> mercadorias = repositorio.findAll();
        Mercadoria mercadoria = mercadorias.stream()
            .filter(m -> m.getId().equals(id))
            .findFirst()
            .orElse(null);
        
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        adicionadorLink.adicionarLink(mercadoria);
        return new ResponseEntity<>(mercadoria, HttpStatus.OK);
    }

    @GetMapping("/mercadorias")
    public ResponseEntity<List<Mercadoria>> obterMercadorias() {
        List<Mercadoria> mercadorias = repositorio.findAll();
        
        if (mercadorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        adicionadorLink.adicionarLink(mercadorias);
        return new ResponseEntity<>(mercadorias, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Mercadoria> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
        if (mercadoria.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Mercadoria mercadoriaSalva = repositorio.save(mercadoria);
        adicionadorLink.adicionarLink(mercadoriaSalva);
        
        return new ResponseEntity<>(mercadoriaSalva, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Mercadoria> atualizarMercadoria(@RequestBody Mercadoria atualizacao) {
        if (atualizacao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Mercadoria> mercadorias = repositorio.findAll();
        Mercadoria mercadoria = mercadorias.stream()
            .filter(m -> m.getId().equals(atualizacao.getId()))
            .findFirst()
            .orElse(null);
        
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.save(atualizacao);
        adicionadorLink.adicionarLink(atualizacao);
        
        return new ResponseEntity<>(atualizacao, HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirMercadoria(@RequestBody Mercadoria exclusao) {
        if (exclusao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Mercadoria> mercadorias = repositorio.findAll();
        Mercadoria mercadoria = mercadorias.stream()
            .filter(m -> m.getId().equals(exclusao.getId()))
            .findFirst()
            .orElse(null);
        
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.delete(mercadoria);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}