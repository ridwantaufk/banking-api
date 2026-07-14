package com.ridwan.banking_api.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;

    // GETTERS
    public String getRefreshToken() {
        return refreshToken;
    }

    // SETTERS
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}