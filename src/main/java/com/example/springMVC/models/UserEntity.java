package com.example.springMVC.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String  password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles", // проміжкова таблиця між users та roles
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<Role>();
    // @ManyToMany - вказує, що між сутностями User i Role існує відношення "багато до багатьох" :
    // один користувач може мати багато ролей, і кожна роль може бути призначена до багатьох користувачів
    // fetch = FetchType.EAGER - вказує, що при завантаженні об'єкт користувача також потрібно вивантажати всі його ролі
    // cascade = CascadeType.ALL - вказує, що всі операції, які відбуваються з об'єктом користувача, також повинні впливати на його ролі
    // тобто, коли користувач зберігається, то і його ролі також будуть збережені.
    // @JoinTable - анотація, що вказує на наявність таблиці посередника для відображення цього відношення між таблицями користувача та ролей
    // joinColumns i inverseJoinColumns - ці параметри вказують стовпці, які використовуються для створення зв'язку між таблицями користувачів і ролей
    // Вони вказують, які стовпці в таблиці користувачів пов'язані зі стовпцями в таблиці ролей за допомогою цієї таблиці-посередника
}
