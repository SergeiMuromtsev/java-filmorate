package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidationTests {
    @Test
    public void validateWithEmptyFields(){
        User user = new User();
        Assertions.assertThrows(ValidateException.class, ()->{user.validate();});
    }

    @Test
    public void validateWithWrongBirthdayFields(){
        User user = new User();
        user.setId();
        user.setName("vasya");
        user.setLogin("vasya01");
        user.setEmail("mail@mail.de");
        user.setBirthday(LocalDate.of(2799, 06,06));
        Assertions.assertThrows(ValidateException.class, ()->{user.validate();});
    }

    @Test
    public void allFieldsOK() throws ValidateException {
        User user = new User();
        user.setId();
        user.setName("vasya");
        user.setLogin("vasya01");
        user.setEmail("mail@mail.de");
        user.setBirthday(LocalDate.of(1799, 06,06));
        Assertions.assertTrue(user.validate());
    }
}
