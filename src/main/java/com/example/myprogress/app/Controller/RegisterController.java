package com.example.myprogress.app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import jakarta.validation.Valid;

@RequestMapping("/register")
@RestController
public class RegisterController {

    private final RegisterGeneral registerGeneral;
private final caloriesIntakeService caloriesIntakeService;


    public RegisterController(RegisterGeneral registerGeneral, caloriesIntakeService caloriesIntakeService) {
        this.registerGeneral = registerGeneral;
        this.caloriesIntakeService = caloriesIntakeService;
    }

    // lacked of it send the data the user related with its progress, Right now the
    // new user now are registered in their own table(User,email,pas,type)
    @PostMapping("/User")
    public ResponseEntity<?> registerUserApp(
          @Validated(ValidationOnlyRegisterGroup.class) @RequestBody appUser user) { // I put the app User because this is the entity that has all attributes
                                         // (inclusive the password)
        user.setInfoLogged(new infoLogged());
        if (registerGeneral.RegisterUser(user)) {
            CaloriesIntake newUser = new CaloriesIntake();
            newUser.setId(user.getUser());
            caloriesIntakeService.saveUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerGeneral.getRegister().getMessages());
        }
        throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception

    }
}
