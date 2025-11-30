package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, exclude = {"cliente", "funcionario", "veiculo", "mercadorias", "servicos"})
@Entity
public class Venda extends RepresentationModel<Venda> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date cadastro;
    
    private String identificacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"vendas", "veiculos", "credenciais"})
    private Usuario cliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"vendas", "credenciais"})
    private Usuario funcionario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("vendas")
    private Veiculo veiculo;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Mercadoria> mercadorias = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Servico> servicos = new HashSet<>();
}