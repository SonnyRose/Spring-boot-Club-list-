package com.example.springMVC.controller;


import com.example.springMVC.DTO.RegistrationDTO;
import com.example.springMVC.models.UserEntity;
import com.example.springMVC.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/register")
    public String getRegisterForm(Model model){
        RegistrationDTO user = new RegistrationDTO();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDTO user,
                           BindingResult bindingResult, Model model) {
        UserEntity existingEmailUser = userService.findByEmail(user.getEmail());
        if (existingEmailUser != null && existingEmailUser.getEmail() != null
                && !existingEmailUser.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        UserEntity existingUserName = userService.findByUsername(user.getUsername());
        if (existingEmailUser != null && existingEmailUser.getUsername() != null && !existingEmailUser.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/club?success";
    }
    // @Valid - анотація вказує, що об'єкт user повинен піддаватися перевірці валідації
    // @ModelAttribute("user") RegistrationDTO user - вказує, що параметр user методу контролера повинен бути витягнутий у модель
    // В цьому випадку, об'єкт user передається з шаблону register, як формат реєсрації

}

