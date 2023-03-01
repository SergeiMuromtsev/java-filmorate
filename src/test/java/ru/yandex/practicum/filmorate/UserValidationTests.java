package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidationTests {

    private static UserController controller = new UserController();

    @Test
    public void validateWithEmptyFields(){
        User user = User.builder().build();
        Assertions.assertThrows(ValidateException.class, ()->controller.validate(user));
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
        Assertions.assertThrows(ValidateException.class, ()->controller.validate(user));
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
        Assertions.assertDoesNotThrow(()-> controller.validate(user));
    }
}
