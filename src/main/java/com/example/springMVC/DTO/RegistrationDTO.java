package com.example.springMVC.DTO;

import com.example.springMVC.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegistrationDTO{
    private Integer id;
    @NotNull(message = "Username should be filled")
    private String username;
    @NotNull(message = "Email should be filled")
    private String email;
    @NotNull(message = "Password should be filled ")
    private String password;
}
