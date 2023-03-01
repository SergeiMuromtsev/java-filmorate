package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private HashMap<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public Collection<User> findAll() {
        log.info("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        log.info("Текущее количество пользователей: {}", users.size());
        try {
            validate(user);
        } catch (ValidateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setId();
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        try {
            validate(user);
        } catch (ValidateException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!users.containsKey(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user for update");
        } else {
            users.put(user.getId(), user);
            return user;
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

