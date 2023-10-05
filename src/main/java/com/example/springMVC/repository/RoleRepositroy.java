package com.example.springMVC.repository;

import com.example.springMVC.models.Role;
import com.example.springMVC.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositroy extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
