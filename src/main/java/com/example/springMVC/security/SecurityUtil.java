package com.example.springMVC.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getSessionUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Отримує поточний об'єкт аутентифікації з контексту безпеки, який зберігає інформацію про користувача, який ввійшов в систему або  надав права доступу
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            // перевіряє, чи поточний користувач не є анонімним користувачем
            // AnonymousAuthenticationToken представляє анонімного користувача, який не аутентифікований в системі
            String currentUsername = authentication.getName();
            // Якщо користувач аутентифікований, то отримуємо його ім'я користувача з об'єкту аутентифікації
            return currentUsername;
        }
        return null;
        // Якщо користувач не аутентифікований, то вертаємо null
    }
}
