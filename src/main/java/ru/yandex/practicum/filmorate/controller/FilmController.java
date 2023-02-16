package ru.yandex.practicum.filmorate.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private ArrayList<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Получен запрос список всех фильмов.");
        log.debug("Текущее количество фильмов: {}", films.size());
        return films;
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        log.info("Получен запрос добавление фильма.");
        try {
            if(film.validate()){
                film.setId();
                films.add(film);
            }
        } catch (ValidateException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    @PutMapping("/films")
    public Film update(@RequestBody Film film) {
        log.info("Получен запрос обновление фильма.");
        try {
            if(film.validate()){
                for (Film item: films){
                    if (film.getId() == item.getId()){
                        films.remove(item);
                        films.add(film);
                        return film;
                    }
                }
                throw new ValidateException("Validation failed when put request was attempted");
            }
        } catch (ValidateException e) {
            throw new RuntimeException(e);
        }
        return film;
    }
}