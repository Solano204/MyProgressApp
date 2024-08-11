package com.example.myprogress.app.Exceptions;

public class PasswordIncorrect extends RuntimeException{

    public PasswordIncorrect(String message) {
        super(message);
    }
}
