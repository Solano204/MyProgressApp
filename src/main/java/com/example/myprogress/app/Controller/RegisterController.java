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
import com.example.myprogress.app.GeneralServices.GenerateResponse;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.RedisService.TokenService;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.SpringSecurity.BuildToken;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import lombok.Data;


// This class will be used to register a new user type APP
@RequestMapping("/Register")
@RestController
@CrossOrigin(origins = "*")
@Data
public class RegisterController {

    private final RegisterGeneral registerGeneral;
    private final caloriesIntakeService caloriesIntakeService;
    private final BuildToken buildToken;
    private final PasswordEncoder passwordEncoder;
    private final MessagesFinal messagesFinal;
    private final TokenService tokenService;
    private final GenerateResponse generateResponse;

    // This user will be use to keep the persistence of the user to insert can be
    // Facebook or Google, This I make it because I cant send the user in the
    // process of the authentication of Google or Facebook

    

    // lacked of it send the data the user related with its progress, Right now the
    // new user now are registered in their own table(User,email,pas,type)
    @PostMapping("/App/User")
    public ResponseEntity<?> registerUserApp(@Validated(ValidationOnlyRegisterGroup.class) @RequestBody appUser user) { // I
                                                                                                                        // put
                                                                                                                        // the
                                                                                                                        // app
                                                                                                                        // User
                                                                                                                        // because
        // this is the entity that has all attributes
        user.setPassWord(passwordEncoder.encode(user.getPassWord())); // Encode the password
        user.setInfoLogged(new infoLogged());
        if (!registerGeneral.RegisterUser(user)) {
            throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
        }
        CaloriesIntake newUser = new CaloriesIntake();
        newUser.setId(user.getUser());
        caloriesIntakeService.saveUser(newUser);
        Map<String, Object> body = new HashMap<>();
        generateResponse.generateResponse(user, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
