package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    ArrayList<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("Текущее количество пользователей: {}", users.size());
        return users;
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        log.info("Текущее количество пользователей: {}", users.size());
        try {
            if (user.validate()){
                user.setId();
                users.add(user);
            }
        } catch (ValidateException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        try {
            if(user.validate()){
                for (User item: users){
                    if (user.getId() == item.getId()){
                        users.remove(item);
                        users.add(user);
                        return user;
                    }
                }
                throw new RuntimeException();
            }
        } catch (ValidateException e) {
            throw new RuntimeException(e);
        }
        log.info("Пользователь для обновления не найден");
        return null;
    }
}

