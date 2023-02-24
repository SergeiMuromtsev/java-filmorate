package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidationTests {

    private static FilmController controller = new FilmController();

    @Test
    public void validateWithEmptyFields(){
        Film film = Film.builder().build();
        Assertions.assertThrows(ValidateException.class, ()-> controller.validate(film));
    }

    @Test
    public void validateWithWrongReleaseFields(){
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1799, 6, 6))
                .duration(160)
                .build();

        Assertions.assertThrows(ValidateException.class, ()-> controller.validate(film));
    }

    @Test
    public void allFieldsOK() throws ValidateException {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2000, 6, 6))
                .duration(160)
                .build();

        Assertions.assertDoesNotThrow(()-> controller.validate(film));
    }
}
