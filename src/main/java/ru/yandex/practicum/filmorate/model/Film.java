package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.ValidateException;

import java.time.LocalDate;

@Data
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

    public boolean validate() throws ValidateException {
        if (name == null || name.isBlank()){
            throw new ValidateException("Wrong film name");
        }
        if (description.length() > 200) {
            throw new ValidateException("Wrong length");
        }
        if (releaseDate.isBefore(LocalDate.of(1895,12,28))){
            throw new ValidateException("Wrong release date");
        }
        if (duration <= 0) {
            throw new ValidateException("Wrong duration");
        }
        return true;
    }
}
