package com.example.springMVC.security;

import com.example.springMVC.models.UserEntity;
import com.example.springMVC.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findFirstByUsername(username);
        if (user != null){
            User authorizedUser = new User(user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
            );
            // якщо  user знайдений, то створюється об'єкт User, який реалізує інтерфейс UserDetails
            // До цього об'єкту додаються основні властивості користувача : email, password, roles
            // Ролі користувача перетворюються з формату, який зберігається в базі даних(ADMIN, USER) у об'єкт SimpleGrantedAuthority які представляють ролі в Spring Security
            // Повертається створений об'єкт User як результат методу loadByUserName. Цей об'єкт містить інформацію про користувача, яку SpringSecurity використовує для подальшої обробки аутентифікації та авторизації
            return authorizedUser;
        }else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
