package com.example.myprogress.app.Exceptions;


// This exception will be thrown if this case of the user's insert incorrect data
public class UserExistException extends RuntimeException {

    public UserExistException(String message) {
        super(message);
    }
}
