package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.AdicionadorLinkUsuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {
    
    @Autowired
    private RepositorioUsuario repositorio;
    
    @Autowired
    private AdicionadorLinkUsuario adicionadorLink;

    @GetMapping("/usuarios")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Usuario>> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        
        System.out.println("Total de usuários: " + usuarios.size());
        
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        usuarios.forEach(u -> {
            u.getEmails().size();
            u.getTelefones().size();
            u.getDocumentos().size();
            u.getCredenciais().size();
            u.getVeiculos().size();
            u.getMercadorias().size();
            u.getPerfis().size();
        });
        
        adicionadorLink.adicionarLink(usuarios);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
        System.out.println("Buscando usuário ID: " + id);
        
        return repositorio.findById(id)
            .map(usuario -> {
                System.out.println("Encontrado: " + usuario.getNome());
                
                usuario.getEmails().size();
                usuario.getTelefones().size();
                usuario.getDocumentos().size();
                usuario.getCredenciais().size();
                usuario.getVeiculos().size();
                usuario.getMercadorias().size();
                usuario.getPerfis().size();
                
                adicionadorLink.adicionarLink(usuario);
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            })
            .orElseGet(() -> {
                System.err.println("Usuário não encontrado: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            });
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = repositorio.save(usuario);
        adicionadorLink.adicionarLink(novoUsuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return repositorio.findById(usuario.getId())
            .map(existente -> {
                Usuario atualizado = repositorio.save(usuario);
                adicionadorLink.adicionarLink(atualizado);
                return new ResponseEntity<>(atualizado, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/excluir")
    @Transactional
    public ResponseEntity<Void> excluirUsuario(@RequestBody Usuario usuario) {
        if (usuario.getId() != null && repositorio.existsById(usuario.getId())) {
            repositorio.deleteById(usuario.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}