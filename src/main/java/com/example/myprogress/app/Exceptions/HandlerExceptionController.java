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

    @ExceptionHandler(UnsuccessfulLoginException.class)
    public ResponseEntity<Error> unsuccessfulLogin(Exception ex) { 
        Error error = new Error();
        error.setDate(LocalDate.now());
        error.setError("Error trying to Login");
        error.setMessage(ex.getMessage());
        error.setNumberStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
        return  ResponseEntity.internalServerError().body(error);
    }
    
    // This exception will execute when the user already exist
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

    // This exception will execute when the email already exist
    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<Error> unsuccessfulExitsEmail(Exception ex) { 
        Error error = new Error();
        error.setDate(LocalDate.now());
        error.setError("Please Verify your email");
        error.setMessage(ex.getMessage());
        error.setNumberStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
        return  ResponseEntity.internalServerError().body(error);
    }

    // This exception will execute when the password is incorrect exist
    @ExceptionHandler(PasswordIncorrect.class)
    public ResponseEntity<Error> unsuccessfulPassword(Exception ex) { 
        Error error = new Error();
        error.setDate(LocalDate.now());
        error.setError("Please Verify your password");
        error.setMessage(ex.getMessage());
        error.setNumberStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
        return  ResponseEntity.internalServerError().body(error);
    }

    // This exception will execute when some incoming values are incorrect
    @ExceptionHandler(FieldIncorrectException.class)
    public ResponseEntity<Error> fieldIncorrects(FieldIncorrectException ex) { 
        Error error = new Error();
        error.setDate(LocalDate.now());
        error.setError("Try it again");
        error.setMessage(ex.getMessage());
        error.setNumberStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
        return  ResponseEntity.internalServerError().body(error);
    }
}
