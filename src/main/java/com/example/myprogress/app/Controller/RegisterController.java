package com.example.myprogress.app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.SpringSecurity.BuildToken;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;

@RequestMapping("/register")
@RestController
public class RegisterController {
    
    private final RegisterGeneral registerGeneral;
    private final caloriesIntakeService caloriesIntakeService;
    private final BuildToken buildToken;
    private final PasswordEncoder passwordEncoder;
    private final MessagesFinal messagesFinal;

    public RegisterController(RegisterGeneral registerGeneral, BuildToken buildToken, PasswordEncoder passwordEncoder,  caloriesIntakeService caloriesIntakeService, MessagesFinal messagesFinal) {
        this.messagesFinal = messagesFinal;
        this.registerGeneral = registerGeneral;
        this.caloriesIntakeService = caloriesIntakeService;
        this.passwordEncoder = passwordEncoder;

        this.buildToken = buildToken;
    }

    // lacked of it send the data the user related with its progress, Right now the
    // new user now are registered in their own table(User,email,pas,type)
    @PostMapping("/User")
    public ResponseEntity<?> registerUserApp(@RequestBody appUser user) { // I put the app User because
                                                                          // this is the entity that has
                                                                          // all attributes
        user.setPassWord(passwordEncoder.encode(user.getPassWord())); // Encode the password
        
        user.setInfoLogged(new infoLogged());
        if (!registerGeneral.RegisterUser(user)) {
            throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
        }
        CaloriesIntake newUser = new CaloriesIntake();
        newUser.setId(user.getUser());
        caloriesIntakeService.saveUser(newUser);
        // (inclusive the password)
        user.setInfoLogged(new infoLogged());
        Map<String, Object> body = new HashMap<>();
        body.put("token", buildToken.generateToken(user.getUser(), 3600000));
        body.put("RefreshToken", buildToken.generateToken(user.getUser(), (7 * 24 * 60 * 60 * 1000)));
        messagesFinal.fillMapInformation(body, registerGeneral.getUserRegistered(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
