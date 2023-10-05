package com.example.springMVC.models;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data // генерує equals, hashCode, toString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder // при створенні обєкта буде вносити дані за порядком створення
@Table(name = "club")
public class Club {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String title;
    private String photourl;
    private String content;
    @CreationTimestamp // автоматично додає дату
    private LocalDateTime createdOn;
    @UpdateTimestamp // автоматично оновлює дату
    private LocalDateTime updatedOn;
    private String created_by;
    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false) // Змініть "created_by_id" на назву відповідного стовпця в базі даних
    private UserEntity createdBy;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Event> events = new ArrayList<>();



}
