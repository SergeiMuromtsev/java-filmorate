package ru.yandex.practicum.filmorate;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidationTests {
    @Test
    public void validateWithEmptyFields(){
        Film film = new Film();
        Assertions.assertThrows(ValidateException.class, ()->{film.validate();});
    }

    @Test
    public void validateWithWrongReleaseFields(){
        Film film = new Film();
        film.setId();
        film.setDescription("dscrptn");
        film.setDuration(100);
        film.setName("name");
        film.setReleaseDate(LocalDate.of(1799, 06,06));
        Assertions.assertThrows(ValidateException.class, ()->{film.validate();});
    }

    @Test
    public void allFieldsOK() throws ValidateException {
        Film film = new Film();
        film.setId();
        film.setDescription("dscrptn");
        film.setDuration(100);
        film.setName("name");
        film.setReleaseDate(LocalDate.of(2000, 06,06));
        Assertions.assertTrue(film.validate());
    }
}
