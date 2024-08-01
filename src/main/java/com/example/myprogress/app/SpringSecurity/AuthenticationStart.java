package com.example.myprogress.app.SpringSecurity;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.myprogress.app.Entites.appUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// THIS CLASS CREATE THE TOKEN, And How extends of this class UserNameEtc is a special class and automacally will execute only in the url login
public class AuthenticationStart extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    public AuthenticationStart(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /* The request contains the json or user's login information was sent */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        appUser userGet = null;
        String userName = "";
        String password = "";
        try {
            userGet = new ObjectMapper().readValue(request.getInputStream(), appUser.class); // Here the json was sent that
                                                                                          // contains user's information
                                                                                          // I mapping it to a object of
                                                                                          // the have (USER)
            userName = userGet.getUser();
            password = userGet.getPassWord();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,
                password); // Here I save the password and user of the user to send and will be check if
                           // its information exists in the database
        return authenticationManager.authenticate(authenticationToken); // I send the user's information to Method(
                                                                        // loadUserByUsername (UserDetailsService) of
                                                                        // the other class, Here I comprobate if exist) Because the authenticate manager use these classess userDatails and EncoderPass to validate the data user
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        System.out.println("SUCESSFULL");

        org.springframework.security.core.userdetails.User userGet = (org.springframework.security.core.userdetails.User) authResult
                .getPrincipal(); // Here I get the DetailUser returned by Class ValidateToken
        String userName = userGet.getUsername();
        Collection<? extends GrantedAuthority> listRoles = authResult.getAuthorities(); // I get user's permissions
        Claims claims = Jwts.claims().add("authorities", new ObjectMapper().writeValueAsString(listRoles)).build();// Hee I add the roles in the token
            // Here Add information in the token
            String token = Jwts.builder().subject(userName)
                    .expiration(new Date(System.currentTimeMillis() + 3600000))
                    .issuedAt(new Date())
                    .claims(claims)
                    .signWith(VariablesGeneral.SECRET_KEY)
                    .compact(); // Here I generate the token final to assign to user
        response.addHeader(VariablesGeneral.AUTHORIZATION, VariablesGeneral.HEADER_TOKEN + token); // it generates the
                                                                                                   // full token with headers

        // I send a response to the user (token,user,)
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", userName);
        body.put("message", "The token was made with success");
        response.getWriter().write(new ObjectMapper().writeValueAsString(body)); // The ObjectMapper is used to convert the Map to a JSON string, which is then written to the response body.
        response.setStatus(200);
        response.setContentType(VariablesGeneral.CONTENT_TYPE); // i give the message in format JSON
    }

    // The validation was unsucess   
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        Map<String, String> body = new HashMap<>();
        body.put("message", "Error in the authentication process, the token wasnt generated ");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(VariablesGeneral.CONTENT_TYPE);
    }

}
