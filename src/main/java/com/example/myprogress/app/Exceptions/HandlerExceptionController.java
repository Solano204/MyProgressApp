package com.example.myprogress.app.Exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptionController {
    // The method with exception more specific have higher priority to send the message binding error
    @ExceptionHandler(UnsuccessfulRegisterException.class)
    public ResponseEntity<Error> unsuccessfulRegister(Exception ex) { 
        Error error = new Error();
        error.setDate(LocalDate.now());
        error.setError("Error trying to register");
        error.setMessage(ex.getMessage());
        error.setNumberStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
        return  ResponseEntity.internalServerError().body(error);
    }
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Error> unsuccessfulExitsUser(Exception ex) { 
        Error error = new Error();
        error.setDate(LocalDate.now());
        error.setError("Please Verify your user");
        error.setMessage(ex.getMessage());
        error.setNumberStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
        return  ResponseEntity.internalServerError().body(error);
    }
}
