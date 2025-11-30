package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.AdicionadorLinkEmpresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/empresa")
public class EmpresaControle {
    
    @Autowired
    private RepositorioEmpresa repositorio;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private AdicionadorLinkEmpresa adicionadorLink;

    @GetMapping("/empresas")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Empresa>> obterEmpresas() {
        List<Empresa> empresas = repositorio.findAll();
        
        empresas.forEach(empresa -> {
            empresa.getUsuarios().size();
            empresa.getMercadorias().size();
            empresa.getServicos().size();
            empresa.getVendas().size();
            empresa.getTelefones().size();
        });
        
        adicionadorLink.adicionarLink(empresas);
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Empresa> obterEmpresa(@PathVariable Long id) {
        return repositorio.findById(id)
            .map(empresa -> {
                empresa.getUsuarios().size();
                empresa.getMercadorias().size();
                empresa.getServicos().size();
                empresa.getVendas().size();
                empresa.getTelefones().size();
                
                adicionadorLink.adicionarLink(empresa);
                return new ResponseEntity<>(empresa, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Empresa> cadastrarEmpresa(@RequestBody Empresa empresa) {
        Empresa novaEmpresa = repositorio.save(empresa);
        return new ResponseEntity<>(novaEmpresa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        return repositorio.findById(id)
            .map(empresaExistente -> {
                empresa.setId(id);
                Empresa atualizada = repositorio.save(empresa);
                return new ResponseEntity<>(atualizada, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEmpresa(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{empresaId}/usuario/{usuarioId}")
    @Transactional
    public ResponseEntity<Empresa> associarUsuario(
            @PathVariable Long empresaId, 
            @PathVariable Long usuarioId) {
        
        return repositorio.findById(empresaId)
            .flatMap(empresa -> repositorioUsuario.findById(usuarioId)
                .map(usuario -> {
                    empresa.getUsuarios().add(usuario);
                    repositorio.save(empresa);
                    return new ResponseEntity<>(empresa, HttpStatus.OK);
                }))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{empresaId}/usuarios")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Usuario>> obterUsuariosDaEmpresa(@PathVariable Long empresaId) {
        return repositorio.findById(empresaId)
            .map(empresa -> {
                List<Usuario> usuarios = empresa.getUsuarios().stream().toList();
                return new ResponseEntity<>(usuarios, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{empresaId}/usuario/{usuarioId}")
    @Transactional
    public ResponseEntity<Void> removerUsuarioDaEmpresa(
            @PathVariable Long empresaId, 
            @PathVariable Long usuarioId) {
        
        return repositorio.findById(empresaId)
            .map(empresa -> {
                empresa.getUsuarios().removeIf(u -> u.getId().equals(usuarioId));
                repositorio.save(empresa);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}