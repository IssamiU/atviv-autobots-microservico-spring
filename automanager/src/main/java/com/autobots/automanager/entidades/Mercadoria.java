package com.autobots.automanager.entidades;

import java.util.Date;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mercadoria extends RepresentationModel<Mercadoria> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private Date validade;
    
    @Column
    private Date fabricao;
    
    @Column
    private Date cadastro;
    
    @Column
    private String nome;
    
    @Column
    private Double valor;
    
    @Column
    private Long quantidade;
    
    @Column
    private String descricao;
}