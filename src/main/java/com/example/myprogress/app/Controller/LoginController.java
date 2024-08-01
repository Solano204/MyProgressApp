package com.example.myprogress.app.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.UnsuccessfulLoginException;
import com.example.myprogress.app.LoginService.LoginGeneral;
import com.example.myprogress.app.validations.RegisterInformation;

import jakarta.validation.Valid;

@RequestMapping("/login")
@RestController
public class LoginController {
    private  LoginGeneral loginGeneral;

    public LoginController(LoginGeneral loginGeneral) {
        this.loginGeneral = loginGeneral;
    }

    // lacked of it send the data the user related with its progress, Right now the
    // new user now are logined in their own table(User,email,pas,type)
    @PostMapping("/User")
    public ResponseEntity<?> LoginUserApp(@Valid @RequestBody appUser user) { // I put the app User because this is the entity that has all attributes
                                         // (inclusive the password)
        user.setInfoLogged(new infoLogged());
        user.setRegisterInformation(new InfoRegister());
        if (loginGeneral.LoginUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(loginGeneral.getLogin().getMessages());
        }
        throw new UnsuccessfulLoginException("The user couldn't be logined"); // throw an exception
    }
}   

