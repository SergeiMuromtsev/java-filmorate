package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.time.LocalDate;

public class FilmValidationTests {

    private static FilmStorage filmStorage;



    @Test
    public void validateWithEmptyFields(){
        Film film = Film.builder().build();
        Assertions.assertThrows(ValidateException.class, ()-> filmStorage.validate(film));
    }

    @Test
    public void validateWithWrongReleaseFields(){
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1799, 6, 6))
                .duration(160)
                .build();

        Assertions.assertThrows(ValidateException.class, ()-> filmStorage.validate(film));
    }

    @Test
    public void allFieldsOK() throws ValidateException {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2000, 6, 6))
                .duration(160)
                .build();

        Assertions.assertDoesNotThrow(()-> filmStorage.validate(film));
    }
}
