package com.autobots.automanager.enumeracoes;

public enum PerfilUsuario {
    ADMINISTRADOR("ROLE_ADMINISTRADOR"),
    GERENTE("ROLE_GERENTE"),
    VENDEDOR("ROLE_VENDEDOR"),
    CLIENTE("ROLE_CLIENTE");

    private String role;

    PerfilUsuario(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}