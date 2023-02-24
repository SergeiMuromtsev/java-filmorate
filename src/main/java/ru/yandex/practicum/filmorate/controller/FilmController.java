package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.*;

/*Привет! Болел всю неделю, долго не мог доделать работу.
* По исключениям: понял что у меня не так было, но не до конца понял, как в итоге должно быть. Если снова не так, как
* нужно сделал, напиши поподробнее пожалуйста.*/

@RestController
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private HashMap <Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        log.info("Получен запрос добавление фильма.");
        try {
            validate(film);
        } catch (ValidateException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Unable to find resource");
        }
        film.setId();
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film update(@RequestBody Film film) {
        log.info("Получен запрос обновление фильма.");
        try {
            validate(film);
        } catch (ValidateException e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
        if (!films.containsKey(film.getId())) {
            throw new ResponseStatusException(NOT_FOUND, "No film for update");
        } else {
            films.put(film.getId(), film);
        }
        return film;
    }

    public void validate(Film film) throws ValidateException {
        if (film.getName() == null || film.getName().isBlank()){
            throw new ValidateException("Wrong film name");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidateException("Wrong length");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidateException("Wrong release date");
        }
        if (film.getDuration() <= 0) {
            throw new ValidateException("Wrong duration");
        }
    }
}