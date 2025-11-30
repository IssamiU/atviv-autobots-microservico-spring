package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelos.AdicionadorLinkVeiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {
    
    @Autowired
    private RepositorioVeiculo repositorio;
    
    @Autowired
    private AdicionadorLinkVeiculo adicionadorLink;

    @GetMapping("/veiculos")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Veiculo>> obterVeiculos() {
        List<Veiculo> veiculos = repositorio.findAll();
        
        System.out.println("Total de veículos: " + veiculos.size());
        
        if (veiculos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        veiculos.forEach(v -> {
            if (v.getProprietario() != null) {
                v.getProprietario().getNome();
            }
            v.getVendas().size();
        });
        
        adicionadorLink.adicionarLink(veiculos);
        return new ResponseEntity<>(veiculos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Veiculo> obterVeiculo(@PathVariable Long id) {
        System.out.println("Buscando veículo ID: " + id);
        
        return repositorio.findById(id)
            .map(veiculo -> {
                System.out.println("Encontrado: " + veiculo.getPlaca());
                
                if (veiculo.getProprietario() != null) {
                    veiculo.getProprietario().getNome();
                }
                veiculo.getVendas().size();
                
                adicionadorLink.adicionarLink(veiculo);
                return new ResponseEntity<>(veiculo, HttpStatus.OK);
            })
            .orElseGet(() -> {
                System.err.println("Veículo não encontrado: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            });
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<Veiculo> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        Veiculo novoVeiculo = repositorio.save(veiculo);
        adicionadorLink.adicionarLink(novoVeiculo);
        return new ResponseEntity<>(novoVeiculo, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity<Veiculo> atualizarVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return repositorio.findById(veiculo.getId())
            .map(existente -> {
                Veiculo atualizado = repositorio.save(veiculo);
                adicionadorLink.adicionarLink(atualizado);
                return new ResponseEntity<>(atualizado, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/excluir")
    @Transactional
    public ResponseEntity<Void> excluirVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo.getId() != null && repositorio.existsById(veiculo.getId())) {
            repositorio.deleteById(veiculo.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}