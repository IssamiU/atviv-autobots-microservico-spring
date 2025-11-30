package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.AdicionadorLinkVenda;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/venda")
public class VendaControle {
    
    @Autowired
    private RepositorioVenda repositorio;
    
    @Autowired
    private AdicionadorLinkVenda adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Venda> obterVenda(@PathVariable Long id) {
        List<Venda> vendas = repositorio.findAll();
        Venda venda = vendas.stream()
            .filter(v -> v.getId().equals(id))
            .findFirst()
            .orElse(null);
        
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        adicionadorLink.adicionarLink(venda);
        return new ResponseEntity<>(venda, HttpStatus.OK);
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<Venda>> obterVendas() {
        List<Venda> vendas = repositorio.findAll();
        
        if (vendas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        adicionadorLink.adicionarLink(vendas);
        return new ResponseEntity<>(vendas, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Venda> cadastrarVenda(@RequestBody Venda venda) {
        if (venda.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Venda vendaSalva = repositorio.save(venda);
        adicionadorLink.adicionarLink(vendaSalva);
        
        return new ResponseEntity<>(vendaSalva, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Venda> atualizarVenda(@RequestBody Venda atualizacao) {
        if (atualizacao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Venda> vendas = repositorio.findAll();
        Venda venda = vendas.stream()
            .filter(v -> v.getId().equals(atualizacao.getId()))
            .findFirst()
            .orElse(null);
        
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.save(atualizacao);
        adicionadorLink.adicionarLink(atualizacao);
        
        return new ResponseEntity<>(atualizacao, HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirVenda(@RequestBody Venda exclusao) {
        if (exclusao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        List<Venda> vendas = repositorio.findAll();
        Venda venda = vendas.stream()
            .filter(v -> v.getId().equals(exclusao.getId()))
            .findFirst()
            .orElse(null);
        
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.delete(venda);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}