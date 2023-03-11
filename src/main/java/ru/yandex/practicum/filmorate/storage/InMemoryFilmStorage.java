package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;



import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private HashMap<Integer, Film> films;

    @Autowired
    public InMemoryFilmStorage(HashMap<Integer, Film> films) {
        this.films = films;
    }

    @Override
    public List<Film> findAll() {
        log.info("Текущее количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        log.info("POST /films/{}", film);
        validate(film);
        film.setId();
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        log.info("UPDATE /films/{}", film);
        try {
            validate(film);
        } catch (ValidateException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Unable to find resource");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(int id) {
        if (films.containsKey(id)){
            log.info("GET /films/{}", id);
            return films.get(id);
        }
        else {
            log.info("ID was not found");
            return null;
        }
    }

    @Override
    public Film deleteById(int id) {
        if (films.containsKey(id)){
            log.info("DELETE /films/{}", id);
            Film film = films.get(id);
            films.remove(id);
            return film;
        }
        else {
            log.info("ID was not found");
            return null;
        }
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

    public List<Film> getBestFilms(int count) {
        return films.values().stream().sorted((a, b) -> b.getRate() - a.getRate()).
                limit(count).collect(Collectors.toList());
    }
}
