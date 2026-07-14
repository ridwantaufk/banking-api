package com.ridwan.banking_api.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    com.ridwan.banking_api.entity.User registerUser(com.ridwan.banking_api.dto.RegisterRequest request);
}