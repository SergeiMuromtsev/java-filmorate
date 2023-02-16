package ru.yandex.practicum.filmorate;

import java.io.IOException;

public class ValidateException extends IOException {
    public ValidateException(String message){
        super(message);
    }
}
