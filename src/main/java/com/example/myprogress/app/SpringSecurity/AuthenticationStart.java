package com.example.myprogress.app.SpringSecurity;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.GeneralServices.GenerateResponse;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.LoginService.AppLogin;
import com.example.myprogress.app.LoginService.FacebookLogin;
import com.example.myprogress.app.LoginService.GoogleLogin;
import com.example.myprogress.app.LoginService.Login;
import com.example.myprogress.app.LoginService.LoginGeneral;
import com.example.myprogress.app.Repositories.AppUserRepository;
import com.example.myprogress.app.Repositories.FaceUserRepository;
import com.example.myprogress.app.Repositories.GoogleUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// THIS CLASS CREATE THE TOKEN, And How extends of this class UserNameEtc is a special class and automacally will execute only in the url login
public class AuthenticationStart extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final BuildToken buildToken;
    private final LoginGeneral loginGeneral;
    private final AppUserRepository appUserRepository;
    private final MessagesFinal  messagesFinal;
    private final GenerateResponse generateResponse;
    
    @Builder.Default
    private appUser currentUser;

    public AuthenticationStart(AuthenticationManager authenticationManager, BuildToken buildToken, LoginGeneral loginGeneral, AppUserRepository appUserRepository, MessagesFinal messagesFinal, GenerateResponse generateResponse) {
        this.authenticationManager = authenticationManager;
        this.generateResponse = generateResponse;
        this.buildToken = buildToken;
        this.loginGeneral = loginGeneral;
        this.appUserRepository = appUserRepository;
        this.messagesFinal = messagesFinal;
        
    }


    /* The request contains the json or user's login information was sent */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String userName = "";
        String password = "";
                System.out.println("AuthenticationStart");
        try {
            currentUser = new ObjectMapper().readValue(request.getInputStream(), appUser.class); // Here the json was
                                                                                                 // sent
            // that
            // contains user's
            // information
            // I mapping i                                                                                              t to a object
            // of
            // the have (USER)
            userName = currentUser.getUser();
            password = currentUser.getPassWord();

        } catch (Exception e) {
            throw new FieldIncorrectException("Password or user incorrect");
        }
        validaExisting(currentUser);
        
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userName,
                password); // Here I save the password and user of the user to send and will be check if
                           // its information exists in the database
        return authenticationManager.authenticate(authenticationToken); // I send the user's information to Method(
                                                                        // loadUserByUsername (UserDetailsService) of
                                                                        // the other class, Here I comprobate if exist)
                                                                        // Because the authenticate manager use these
                                                                        // classess userDatails and EncoderPass to
                                                                        // validate the data user
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        System.out.println("SUCESSFULL");

        org.springframework.security.core.userdetails.User userGet = (org.springframework.security.core.userdetails.User) authResult
                .getPrincipal(); // Here I get the DetailUser returned by Class ValidateToken
        String userName = userGet.getUsername();

        
        Map<String, Object> body = new HashMap<>();
        generateResponse.setGenerateRefreshToken(true); // Here I active the variable to generate the refresh token because is a login And I need to refreshToken
        generateResponse.generateResponse(currentUser, body);
        response.addHeader(VariablesGeneral.AUTHORIZATION, VariablesGeneral.HEADER_TOKEN + generateResponse.getToken());
        response.addHeader(VariablesGeneral.AUTHORIZATION, VariablesGeneral.HEADER_TOKEN + generateResponse.getRefreshToken());
        ResponseEntity.status(HttpStatus.CREATED).body(body);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType(VariablesGeneral.CONTENT_TYPE); // i give the message in format JSON
    }

    // The validation was unsucess
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
         AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en el user name or password ");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(VariablesGeneral.CONTENT_TYPE);
    }

    public boolean validaExisting(appUser user) {
        Login login = new AppLogin(appUserRepository);
        return login.templateLogin(user);
    }

}
