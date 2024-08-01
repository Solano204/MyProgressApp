package com.example.myprogress.app.Exceptions;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String message) {
        super(message);
    }
}
