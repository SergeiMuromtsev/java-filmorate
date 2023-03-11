package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> findAll(){
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        filmStorage.create(film);
        return film;
    }

    public Film update(Film film) {
        if (filmStorage.getById(film.getId()) != null) {
            return filmStorage.update(film);
        } else {
            throw new ObjectNotFoundException("Update film: No film with ID " + film.getId());
        }
    }

    public Film getFilmById(int id) {
        Film film = filmStorage.getById(id);
        if (film == null) {
            throw new ObjectNotFoundException("Get film by ID: No film with ID " + id);
        }
        return film;
    }

    public Film deleteById(int id) {
        return filmStorage.deleteById(id);
    }

    public Film addLike(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        film.getLikes().add(userId);
        film.setRate(film.getRate() + 1);
        return film;
    }

    public Film removeLike(int filmId, int userId) {
        Film film = getFilmById(filmId);
        if (film != null) {
            if (film.getLikes().contains(userId)) {
                film.getLikes().remove(userId);
                filmStorage.update(film);
            } else {
                throw new ObjectNotFoundException("Remove like: Like for " + filmId + " from " + userId + " not found");
            }
        }
        return film;
    }

    public List<Film> getBestFilms(int count) {
        return filmStorage.getBestFilms(count);
    }
}
