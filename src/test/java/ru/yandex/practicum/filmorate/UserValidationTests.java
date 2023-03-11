package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.HashMap;

public class UserValidationTests {

    private static UserStorage userStorage = new InMemoryUserStorage(new HashMap<>());

    @Test
    public void validateWithEmptyFields(){
        User user = User.builder().build();
        Assertions.assertThrows(ValidateException.class, ()-> userStorage.validate(user));
    }

    @Test
    public void validateWithWrongBirthdayFields(){
        User user = User.builder()
                .name("vasya")
                .login("vasya01")
                .email("mail@mail.de")
                .birthday(LocalDate.of(2799, 6,6))
                .build();
        user.setId();
        Assertions.assertThrows(ValidateException.class, ()-> userStorage.validate(user));
    }

    @Test
    public void allFieldsOK() throws ValidateException {
        User user = User.builder()
                .name("vasya")
                .login("vasya01")
                .email("mail@mail.de")
                .birthday(LocalDate.of(1998, 6,6))
                .build();
        user.setId();
        Assertions.assertDoesNotThrow(()-> userStorage.validate(user));
    }
}
