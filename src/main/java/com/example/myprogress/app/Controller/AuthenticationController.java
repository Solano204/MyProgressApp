package com.example.myprogress.app.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.example.myprogress.app.Entites.AuthLoginRequest;
import com.example.myprogress.app.Entites.AuthResponse;
import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.InfosLogged;
import com.example.myprogress.app.Entites.Token;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.SpringSecurity.UserDetailServiceImpl;

@RestController
@PreAuthorize("permitAll()")
@RequestMapping("/auth")
// name : name of endpoint , description : description of endpoint
@Tag(name = "Authentication", description = "Controller for Authentication")
@Data
@AllArgsConstructor
public class AuthenticationController {

        private UserDetailServiceImpl userDetailService;

        @Operation(
                summary = "Login User",
                description = "Authenticate a user and return the authentication token along with user details.",
                tags = {"Authentications security"},
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication request with username and password",
                    required = true,
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = AuthLoginRequest.class)
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Successful authentication",
                        content = @Content(
                            mediaType = "application/json",
                             schema = @Schema(oneOf = {Token.class, User.class})
                        )
                    )
                }
            )
        @PostMapping("/login")
        public ResponseEntity<Object> login(@RequestBody AuthLoginRequest userRequest) {
                return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
        }
}
