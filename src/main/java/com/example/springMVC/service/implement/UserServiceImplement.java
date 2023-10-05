package com.example.springMVC.service.implement;

import com.example.springMVC.DTO.RegistrationDTO;
import com.example.springMVC.models.Role;
import com.example.springMVC.models.UserEntity;
import com.example.springMVC.repository.RoleRepositroy;
import com.example.springMVC.repository.UserRepository;
import com.example.springMVC.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImplement implements UserService {
    private UserRepository userRepository;
    private RoleRepositroy roleRepositroy;
    private PasswordEncoder passwordEncoder;

    public UserServiceImplement(UserRepository userRepository,
                                RoleRepositroy roleRepositroy,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepositroy = roleRepositroy;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void saveUser(RegistrationDTO registrationDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword())); // після збереження user у базу даних, шифруємо його пароль
        Role role = roleRepositroy.findByName("USER");
        user.setRoles(Arrays.asList(role)); // Цей вираз створює список із однією роллю, яка передається як аргумент. Тобто, ви передаєте список, який містить одну роль.
        userRepository.save(user);
    }
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
