package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data @Builder
public class Film {
    private static Integer lastId = 1;
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;

    public Integer setId(){
        id = lastId;
        lastId++;
        return id;
    }
}
