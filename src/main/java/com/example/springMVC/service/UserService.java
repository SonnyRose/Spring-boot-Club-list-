package com.example.springMVC.service;

import com.example.springMVC.DTO.RegistrationDTO;
import com.example.springMVC.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDTO registrationDTO);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}
