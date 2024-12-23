package com.example.teachhubproject.dto;

public class LoginResponse {
    private String jwt;
    private String role;
    private Long id;

    // Getters et setters

    public String getJwt() {
        return jwt;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
