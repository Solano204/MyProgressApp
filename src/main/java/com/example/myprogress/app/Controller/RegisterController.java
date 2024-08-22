package com.example.myprogress.app.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

import com.example.myprogress.app.Entites.AuthLoginRequest;
import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.InfosLogged;
import com.example.myprogress.app.Entites.Token;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.GeneralServices.GenerateResponse;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.RedisService.TokenServices;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.SpringSecurity.BuildToken;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

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
    private final TokenServices tokenService;
    private final GenerateResponse generateResponse;

    // This user will be use to keep the persistence of the user to insert can be
    // Facebook or Google, This I make it because I cant send the user in the
    // process of the authentication of Google or Facebook

    

    // lacked of it send the data the user related with its progress, Right now the
    // new user now are registered in their own table(User,email,pas,type)



    @Operation(
    summary = "Register a new user",
    description = "Registers a new user by creating an account with provided details. The password is encoded before saving, and additional user-related data is initialized. A response with a refresh token and other user details is generated upon successful registration.",
    tags = {"User Management"},
    requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The user details required for registration. The request body must include the user’s attributes such as username, password, and other relevant fields.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = appUser.class)

        ),
        required = true
    ),
    responses = {
        @ApiResponse(
            responseCode = "201",
            description = "User created successfully with a response including user details and refresh token",
            content = @Content(
                mediaType = "application/json",
                          schema = @Schema(oneOf = {Token.class,appUser.class})

            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error occurred during user registration",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "string", example = "Internal server error occurred during user registration")
            )
        )
    }
)
    @PostMapping("/App/User")
    public ResponseEntity<?> registerUserApp(@Validated(ValidationOnlyRegisterGroup.class) @RequestBody appUser user) { // I
                                                                                                                        // put
                                                                                                                        // the
                                                                                                                        // app
                                                                                                                        // User
                                                                                                                        // because
        // this is the entity that has all attributes
        user.setPassWord(passwordEncoder.encode(user.getPassWord())); // Encode the password
        user.setInfoLogged(new InfosLogged());
        if (!registerGeneral.RegisterUser(user)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El usuario no se pudo crear");
        }

        CaloriesIntake newUser = new CaloriesIntake();
        newUser.setId(user.getUser());
        caloriesIntakeService.saveUser(newUser);
        Map<String, Object> body = new HashMap<>();
        generateResponse.setGenerateRefreshToken(true); // Here I active the variable to generate the refresh token because is a register And I need to refreshToken
        generateResponse.generateResponse(user, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
