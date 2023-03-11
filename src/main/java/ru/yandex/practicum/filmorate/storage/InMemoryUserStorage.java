package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    HashMap<Integer, User> users;

    @Autowired
    public InMemoryUserStorage(HashMap<Integer, User> users) {
        this.users = users;
    }

    @Override
    public List<User> findAll() {
        log.info("Текущее количество фильмов: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        log.info("POST /users/{}", user);
        validate(user);
        user.setId();
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        log.info("UPDATE /users/{}", user);
        try {
            validate(user);
        } catch (ValidateException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Unable to find resource");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(int id) {
        if (users.containsKey(id)){
            log.info("GET /users/{}", id);
            return users.get(id);
        }
        else {
            log.info("ID was not found");
            return null;
        }
    }

    @Override
    public User deleteById(int id) {
        if (users.containsKey(id)){
            log.info("DELETE /users/{}", id);
            User user = users.get(id);
            users.remove(id);
            return user;
        }
        else {
            log.info("ID was not found");
            return null;
        }
    }

    public void validate(User user){
        if(user.getEmail() == null || !user.getEmail().contains("@") || user.getEmail().isBlank()) {
            throw new ValidateException("Wrong email");
        }
        if(user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")){
            throw new ValidateException("Wrong login");
        }
        if(user.getName() == null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        if(user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidateException("Wrong birthday");
        }
    }
}
