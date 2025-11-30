package com.autobots.automanager.modelos;

import lombok.Data;

@Data
public class LoginRequest {
    private String nomeUsuario;
    private String senha;
}