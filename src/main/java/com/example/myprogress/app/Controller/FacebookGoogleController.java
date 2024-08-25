package com.example.myprogress.app.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.InfosLogged;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Exceptions.UserExistException;
import com.example.myprogress.app.GeneralServices.GenerateResponse;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.LoginService.LoginGeneral;
import com.example.myprogress.app.RedisService.TokenServices;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.Repositories.GoogleUserRepository;
import com.example.myprogress.app.SpringSecurity.BuildToken;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

//This class is for Facebook and Google
@Data
@Hidden // This allow me to hidden the endpoint in swagger
@RestController
@CrossOrigin(origins = "*")
public class FacebookGoogleController {
    private final GoogleUserRepository googleUserRepository;
    private final TokenServices tokenService;
    private appUser currentUserToRegisterGF;
    private final BuildToken buildToken;
    private final RegisterGeneral registerGeneral;
    private final MessagesFinal messagesFinal;
    private final LoginGeneral loginGeneral;
    private final GenerateResponse generateResponse;
    private String currentProcess; //Here I call the current process of the user (Login or Register) This will be used to choose the right process after the user is logged in with Facebook or Google


    // This method is charge to register the user Type Google
    @PostMapping("/Register/Google/User")
    public void registerUserGoogle(@Validated(ValidationOnlyRegisterGroup.class) @RequestBody appUser user, HttpServletResponse response) throws IOException { 
        user.setInfoLogged(new InfosLogged());
        if (googleUserRepository.ExistUser(user.getUser())) {
            throw new UserExistException("Lo sentimos ya existe una persona con ese usuario");
        }
        String redirectServer;
        currentUserToRegisterGF = user;
        if (user.getTypeAuthentication().equalsIgnoreCase("Google")) {
            redirectServer = "http://localhost:8080/oauth2/authorization/google"; // I redirect to the Google Server
        }else{
            redirectServer = "http://localhost:8080/oauth2/authorization/facebook";
        }
        currentProcess = "Register";
        response.sendRedirect(redirectServer);
    }


    // This method is charge to login the user Type Google
    @PostMapping("/Login/Google/User")
    public void registerLoginGoogle( @RequestBody appUser user, HttpServletResponse response) throws IOException { 
        user.setInfoLogged(new InfosLogged());
        
        if (!googleUserRepository.ExistUser(user.getUser())) {
            
            throw new UserExistException("Lo sentimos Ese usuario no existe");
        }

        String redirectServer;
        currentUserToRegisterGF = user;
        if (user.getTypeAuthentication().equalsIgnoreCase("Google")) {
            redirectServer = "http://localhost:8080/oauth2/authorization/google"; // I redirect to the Google Server
        }else{
            redirectServer = "http://localhost:8080/oauth2/authorization/facebook";
        }
        currentProcess = "Login";
        response.sendRedirect(redirectServer);
    }
    
    // This method is charge to load all infromation of the user logged or register DONT METTER IF IS GOOGLE OR FACEBOOK
        @GetMapping("/Authentication/SuccessfulAuthentication")
        public  ResponseEntity<?> successAuthentication()
                throws IOException {
                    Map<String, Object> body = new HashMap<>();
                    generateResponse.setGenerateRefreshToken(true); // Here I active the variable to generate the refresh token because is a login And I need to refreshToken
                    generateResponse.generateResponse(currentUserToRegisterGF, body);
                    return ResponseEntity.status(HttpStatus.CREATED).body(body);

        }
}