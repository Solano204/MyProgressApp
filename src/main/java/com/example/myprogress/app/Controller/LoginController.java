package com.example.myprogress.app.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.UnsuccessfulLoginException;
import com.example.myprogress.app.LoginService.LoginGeneral;
import com.example.myprogress.app.SpringSecurity.BuildToken;

import jakarta.validation.Valid;

@RequestMapping("/login")
@RestController
public class LoginController {
    private LoginGeneral loginGeneral;  
    private final BuildToken buildToken;
    private final PasswordEncoder passwordEncoder;

    public LoginController(LoginGeneral loginGeneral, BuildToken buildToken, PasswordEncoder passwordEncoder) {
        this.loginGeneral = loginGeneral;
        this.buildToken = buildToken;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/User")
    public ResponseEntity<?> LoginUserApp(@Valid @RequestBody appUser user) { // I put the app User because this is the
                                                                              // entity that has all attributes
        // (inclusive the password)
        user.setInfoLogged(new infoLogged());
        user.setRegisterInformation(new InfoRegister());
        user.setPassWord(passwordEncoder.encode(user.getPassWord())); // Encode the password
        System.out.println(user.getPassWord());
        if (!loginGeneral.LoginUser(user)) {
            throw new UnsuccessfulLoginException("The user couldn't be logined"); // throw an exception
        }
        Map<String, Object> body = new HashMap<>();
        body.put("token", buildToken.generateToken(user.getUser(), 3600000));
        body.put("RefreshToken", buildToken.generateToken(user.getUser(), (7 * 24 * 60 * 60 * 1000)));
        body.put("user", loginGeneral.getUserLoged(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
