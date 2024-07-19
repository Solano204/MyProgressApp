package com.example.myprogress.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.RegisterService.RegisterGeneral;

@RequestMapping("/register")
@RestController
public class RegisterController {

    
    private final RegisterGeneral registerGeneral;

    public RegisterController(RegisterGeneral registerGeneral) {
        this.registerGeneral = registerGeneral;
    }

    @PostMapping("/UserApp/{user}/{passWord}/{email}/{typeRegister}")
    public ResponseEntity<?> registerUserApp(
            @PathVariable("user") String user,
            @PathVariable("passWord") String passWord,
            @PathVariable("email") String email,
            @PathVariable("typeRegister") String typeAuthentication) {
        if (registerGeneral.RegisterUser(user, passWord, email, typeAuthentication)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(registerGeneral.getRegister().getMessages());
        }
        throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception

    }
    @PostMapping("/UserFacebook/{user}/{email}/{typeRegister}")
    public ResponseEntity<?> registerUserFacebook(
            @PathVariable("user") String user,
            @PathVariable("email") String email,
            @PathVariable("typeRegister") String typeAuthentication) {
        if (!registerGeneral.RegisterUser(user, "NoPassword", email, typeAuthentication)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(registerGeneral.getRegister().getMessages());
        }
        throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
    }

    @PostMapping("/UserGoogle/{user}/{email}/{typeRegister}")
    public ResponseEntity<?> registerUserGoogle(
            @PathVariable("user") String user,
            @PathVariable("email") String email,
            @PathVariable("typeRegister") String typeAuthentication) {
        if (!registerGeneral.RegisterUser(user, "NoPassword", email, typeAuthentication)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(registerGeneral.getRegister().getMessages());
        }
        throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
    }
}
