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

import com.example.myprogress.app.Entites.AuthLoginRequest;
import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.InfosLogged;
import com.example.myprogress.app.Entites.Token;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.example.myprogress.app.updateInformationService.updateInformationUserService;
import com.example.myprogress.app.validations.RegisterInformation;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.experimental.FieldDefaults;
import io.swagger.v3.oas.annotations.Parameter;

@RequestMapping("/updateIntakeCalories")
@RestController
@Tag(name = "Manage calorie intake")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IntakeCaloriesController {

    caloriesIntakeService caloriesIntakeService;
    updateInformationUserService updateInformationUserService;

    public IntakeCaloriesController(caloriesIntakeService caloriesIntakeService,
            updateInformationUserService updateInformationUserService) {
        this.caloriesIntakeService = caloriesIntakeService;
        this.updateInformationUserService = updateInformationUserService;
    }


    
     @Hidden
    // This method will execute each 24 hours
    @PostMapping("/updateProgress")
    public ResponseEntity<?> updateDataTime(
            @Validated(ValidationOnlyRegisterGroup.class) @RequestBody appUser user) { // Here I active the validation of data linked with the data of process to register because I need that information 
        user.setInfoLogged(new InfosLogged());
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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The user couldn't be updated ");

    }




    
    @Operation(
        summary = "Add Calories Intake",
        description = "Add calories intake information for a user.",
        tags = {"Manage calorie intake"},
        parameters = {
            @Parameter(name = "user",description = "Insert your username", required = true, example = "younowjs2"),
            @Parameter(name = "typeAuthentication", description = "Type of authentication", required = true, example = "App")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Calories intake data",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CaloriesIntake.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Calories intake added successfully",
                content = @Content(
                    mediaType = "application/json"
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                    mediaType = "application/json"
                )
            )
        }
    )
    // This method will execute each time the user decided add new calories
    @PostMapping("/addCalories")
    public ResponseEntity<?> addCalories(
            @RequestParam @DefaultValue("notFound") String user,
            @RequestParam @DefaultValue("notFound") String typeAuthentication,
           @Valid @RequestBody CaloriesIntake caloriesIntake) { // I put the app User because this is the entity that has all
                                                          // attributes
                                                          // (inclusive the password)
        if (user.equals("notFound") || typeAuthentication.equals("notFound")) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Algun campo esta vacio");
        }
        Map<String, Object> response = new HashMap<>();
        appUser appUser = updateInformationUserService.getDataUpdated(user, typeAuthentication);
        caloriesIntakeService.addCaloriesConsumed(caloriesIntake, appUser, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
