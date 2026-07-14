package com.ridwan.banking_api.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    // GETTERS
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // SETTERS
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}