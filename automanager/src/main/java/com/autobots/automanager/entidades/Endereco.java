package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String estado;
    
    @Column
    private String cidade;
    
    @Column
    private String bairro;
    
    @Column
    private String rua;
    
    @Column
    private String numero;
    
    @Column
    private String codigoPostal;
    
    @Column
    private String informacoesAdicionais;
}