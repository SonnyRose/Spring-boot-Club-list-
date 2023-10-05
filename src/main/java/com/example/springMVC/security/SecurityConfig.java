package com.example.springMVC.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    @Bean
    public  static PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
    // хешкодить паролі, що є у базі даних використовуючи алгоритм хешування BCrypt
        // для безпечного зберігання та перевірки паролів користувачів
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        new AntPathRequestMatcher("/login"),
                                        new AntPathRequestMatcher("/register"),
                                        new AntPathRequestMatcher("/club"),
                                        new AntPathRequestMatcher("/css/**"),
                                        new AntPathRequestMatcher("/js/**")
                                )
                                .permitAll()
                                .and()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/club")
                                .loginProcessingUrl("/login")
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
    // @Configuration - анотація, що вказує, що цей клас містить конфігурацію
    // @EnableWebSecurity - активує підтримку для налаштування захисту веб-додатка з використанням Spring Security
    // SecurityFilterChain - об'єкт, який представляє собою конфігурацію фільтрів безпекидля вашого додатка.
   // http.csrf(AbstractHttpConfigurer::disable) - цей рядок вимикає захист CSRF (Cross-Site Request Forgery), але треба бути обережним з виключенням цього методу - це важливий аспект безпеки
    //authorizeRequests(authorizeRequests -> ...) - цей блок налаштовує доступ до різних сторінок та ресурсів
    //Я дозволяю доступ до деяких URL для всіх користувачів permitAll()
    // А інші URL будуть доступні тільки авторизованим користувачам anyRequest().authenticated()
    // formLogin(formLogin -> ...) - налаштування форми входу(логіну)
    // logout(logout -> ...) - налаштуання форми виходу(логауту)

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws  Exception{
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    // AuthenticationManagerBuilder, основна мета цього методу - налаштувати, SpringSecurity повинен виконувати аутентифікацію користувачів
        // authenticationManagerBuilder.userDetailsService(customUserDetailsService) - дає зрозуміти AuthenticationManagerBuilder, що треба використовувати для налаштування власний інтерфейс
        // .passwordEncoder(passwordEncoder()) - вказує, що паролі мають бути зашифрованими
    }
}
