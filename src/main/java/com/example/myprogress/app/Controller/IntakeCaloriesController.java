package com.example.myprogress.app.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.example.myprogress.app.updateInformationService.updateInformationUserService;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import jakarta.validation.Valid;
import lombok.experimental.FieldDefaults;

@RequestMapping("/updateIntakeCalories")
@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IntakeCaloriesController {

    caloriesIntakeService caloriesIntakeService;
    updateInformationUserService updateInformationUserService;

    public IntakeCaloriesController(caloriesIntakeService caloriesIntakeService,
            updateInformationUserService updateInformationUserService) {
        this.caloriesIntakeService = caloriesIntakeService;
        this.updateInformationUserService = updateInformationUserService;
    }

    // This method will execute each 24 hours
    @PostMapping("/updateProgress")
    public ResponseEntity<?> updateDataTime(
            @Validated(ValidationOnlyRegisterGroup.class) @RequestBody appUser user) {
        // (inclusive the password)
        user.setInfoLogged(new infoLogged());
        int caloriesConsumed = caloriesIntakeService.getById(user.getUser()).getCalorieIntake(); // Here I
                                                                                                 // get the
                                                                                                 // total of
                                                                                                 // calories
                                                                                                 // consumed
                                                                                                 // today
        if (updateInformationUserService.updateInformationUser24(user, caloriesConsumed)) {
            // Here I get the new information with the new user
            Map<String, Object> response = new HashMap<>();
            appUser appUser = updateInformationUserService.getDataUpdated(user.getUser(), user.getTypeAuthentication());
            response.put("user", appUser); // I send the user to the front update the new information
            response.put("ProgresObjetive", updateInformationUserService.evaluateObjetive(appUser)); // Here I check if
                                                                                                     // the user already
                                                                                                     // achieved its
                                                                                                     // objective final
        CaloriesIntake caloriesIntake = new CaloriesIntake();
        caloriesIntake.setCalorieIntake(0);
        caloriesIntake.setCarbohydratesConsumed(0);
        caloriesIntake.setFatsConsumed(0);
        caloriesIntake.setProteinsConsumed(0);
            caloriesIntakeService.updateCaloriesIntake(user.getUser(), caloriesIntake); // Here I restart the state of the calories intake
                                                                     // to Start with a new day
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        throw new FieldIncorrectException("The user couldn't be updated "); // throw an exception
    }

    // This method will execute each time the user decided add new calories
    @PostMapping("/addCalories")
    public ResponseEntity<?> addCalories(
            @RequestParam @DefaultValue("notFound") String user,
            @RequestParam @DefaultValue("notFound") String typeAuthentication,
           @Valid @RequestBody CaloriesIntake caloriesIntake) { // I put the app User because this is the entity that has all
                                                          // attributes
                                                          // (inclusive the password)
        if (user.equals("notFound") || typeAuthentication.equals("notFound")) {
             throw new FieldIncorrectException("Sucedio algun error cuando se intento agregar calorias" );
        }
        Map<String, Object> response = new HashMap<>();
        appUser appUser = updateInformationUserService.getDataUpdated(user, typeAuthentication);
        caloriesIntakeService.addCaloriesConsumed(caloriesIntake, appUser, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
