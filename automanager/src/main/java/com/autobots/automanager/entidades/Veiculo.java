package com.autobots.automanager.entidades;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, exclude = {"proprietario", "vendas"}) 
@Entity
public class Veiculo extends RepresentationModel<Veiculo> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String placa;
    private String modelo;
    
    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id")
    @JsonIgnoreProperties({"veiculos", "credenciais", "mercadorias"})
    private Usuario proprietario;
    
    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("veiculo")
    private Set<Venda> vendas = new HashSet<>();
}