package com.example.springMVC.DTO;


import com.example.springMVC.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Builder
public class ClubDTO { // DTO - Об’єкт, який переносить дані між процесами для зменшення кількості викликів
    private Integer id;
    @NotEmpty(message = "Title must to be filled") // антація від spring-validation, щоб неможливо було залишити це поле пустим
    @Size(min = 1 , max = 128)
    private String title;
    @NotEmpty(message = "Photo link must to be filled")
    private String photourl;
    @NotEmpty(message = "Content must to be filled")
    @Size(min = 1)
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDTO> events;
    private UserEntity  createdBy;

}