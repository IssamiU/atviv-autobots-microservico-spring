package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelos.AdicionadorLinkServico;
import com.autobots.automanager.repositorios.RepositorioServico;

@RestController
@RequestMapping("/servico")
public class ServicoControle {
    
    @Autowired
    private RepositorioServico repositorio;
    
    @Autowired
    private AdicionadorLinkServico adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Servico> obterServico(@PathVariable Long id) {
        List<Servico> servicos = repositorio.findAll();
        Servico servico = servicos.stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElse(null);
        
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        adicionadorLink.adicionarLink(servico);
        return new ResponseEntity<>(servico, HttpStatus.OK);
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<Servico>> obterServicos() {
        List<Servico> servicos = repositorio.findAll();
        
        if (servicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        adicionadorLink.adicionarLink(servicos);
        return new ResponseEntity<>(servicos, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Servico> cadastrarServico(@RequestBody Servico servico) {
        if (servico.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Servico servicoSalvo = repositorio.save(servico);
        adicionadorLink.adicionarLink(servicoSalvo);
        
        return new ResponseEntity<>(servicoSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Servico> atualizarServico(@RequestBody Servico atualizacao) {
        if (atualizacao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Servico> servicos = repositorio.findAll();
        Servico servico = servicos.stream()
            .filter(s -> s.getId().equals(atualizacao.getId()))
            .findFirst()
            .orElse(null);
        
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.save(atualizacao);
        adicionadorLink.adicionarLink(atualizacao);
        
        return new ResponseEntity<>(atualizacao, HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirServico(@RequestBody Servico exclusao) {
        if (exclusao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Servico> servicos = repositorio.findAll();
        Servico servico = servicos.stream()
            .filter(s -> s.getId().equals(exclusao.getId()))
            .findFirst()
            .orElse(null);
        
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.delete(servico);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}