package com.autobots.automanager.jwt;

import lombok.Data;
import java.util.Set;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Set<String> perfis;

    public JwtResponse(String token, String username, Set<String> perfis) {
        this.token = token;
        this.username = username;
        this.perfis = perfis;
    }
}