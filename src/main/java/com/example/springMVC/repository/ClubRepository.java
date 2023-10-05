package com.example.springMVC.repository;

import com.example.springMVC.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Integer> { // Integer - тип поля id
    Optional<Club> findByTitle(String url); // метод, що шукає залежності у класі Club та створює код для них

    //JPQL(Java Persistence Query Language) request, що викорисовує JPA(Java Persistence API) для виконання запитів у базі даних
    @Query("SELECT c FROM  Club  c WHERE  c.title LIKE concat('%', :query, '%')")
    List<Club> searchClubs(String query);
    // @Query - анотація JPA, яка дозволяє визначити користувацький JPQL-request
    // SELECT c FROM Club c - застина запиту вказує, що ви хочете обрати об'єкт типу Club з таблиці бази даних, представлений істотою Club
    // WHERE c.title LIKE concat('%', :query, '%') - частина запиту, що визначає умови виборки.
    // Означає, що ви шукаєте об'єкт Club, у яких поле title містить підстрядок, задану змінною :query.
    // Функція LIKE використовується для виконання пошуку з урахуванням шаблону
    // Функція concat('%', :query, '%') використовується для додавання символу % на початку та в кінці значення :query,що дозволяє виконати пошук за підрядком в будь-якому місці поля title
    // List<Club> searchClubs(String query) - виклик методу в інтерфейсі репориторія. Метод приймає параметр типу String(query) та вертає список об'єктів Club, які відповідають умовам запиту

}
