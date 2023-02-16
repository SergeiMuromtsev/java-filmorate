package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import ru.yandex.practicum.filmorate.ValidateException;

import java.time.LocalDate;

@Data
public class User {
    private static Integer lastId = 1;
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    public boolean validate() throws ValidateException {
        if(email == null || !email.contains("@") || email.isBlank()) {
            throw new ValidateException("Wrong email");
        }
        if(login == null || login.isBlank() || login.contains(" ")){
            throw new ValidateException("Wrong login");
        }
        if(name == null || name.isBlank()){
            name = login;
        }
        if(birthday.isAfter(LocalDate.now())){
            throw new ValidateException("Wrong birthday");
        }
        return true;
    }
    public Integer setId(){
        id = lastId;
        lastId++;
        return id;
    }
}
