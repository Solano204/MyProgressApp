package com.example.myprogress.app.SpringSecurity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.AuthLoginRequest;
import com.example.myprogress.app.Entites.AuthResponse;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.GeneralServices.GenerateResponse;
import com.example.myprogress.app.Repositories.AppUserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenerateResponse generateResponse;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<appUser> userOptional = userRepository.findByIdUser(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        appUser userGot = userOptional.get();

        return new org.springframework.security.core.userdetails.User(userGot.getUser(), userGot.getPassWord(),
                true, true, true, true, Collections.emptyList());
    }

    public Map<String, Object> loginUser(AuthLoginRequest authLoginRequest) {

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();
        String login = authLoginRequest.authentication();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> body = new HashMap<>();
        generateResponse.setGenerateRefreshToken(true); // Here I active the variable to generate the refresh token
                                                        // because is a login And I need to refreshToken
        appUser userGot = new appUser();
        userGot.setUser(username);
        userGot.setTypeAuthentication(login);
        generateResponse.generateResponse(userGot, body);
        return body;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
