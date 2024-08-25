package com.example.myprogress.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.myprogress.app.GeneralServices.GenerateResponse;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.LoginService.LoginGeneral;
import com.example.myprogress.app.RedisService.TokenServices;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.Repositories.AppUserRepository;
import com.example.myprogress.app.SpringGoogle.CustomAuthenticationSuccessHandler;
import com.example.myprogress.app.SpringGoogle.CustomTokenValidationFilter;
import com.example.myprogress.app.SpringSecurity.AuthenticationStart;
import com.example.myprogress.app.SpringSecurity.BuildToken;
import com.example.myprogress.app.SpringSecurity.ValidateToken;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import com.example.myprogress.app.RedisService.TokenServices;


@Configuration
@Data
@EnableMethodSecurity(prePostEnabled = true)
public class ConfigurationSecurity {

    // Here I configure the authentication
    private final AuthenticationConfiguration authenticationConfiguration;
    private final BuildToken buildToken;
    private final LoginGeneral loginGeneral;
    private final AppUserRepository repósitory;
    private final MessagesFinal messagesFinal;
    private final OAuth2AuthorizedClientService clientService;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler redirect;
    private final TokenServices tokenService;
    private final GenerateResponse generateResponse;
    private final LogoutHandler logoutHandler;
    private final TokenServices tokenService2;
    private final List<String> urls = List.of(
        "/auth/login", 
        "/Register/App/User",
        "/Authentication/SuccessfulAuthentication",
        "/Register/Google/User",
        "//RefreshToken",
        "/Login/Google/User",
        "/oauth2/authorization/**",
        "/swagger-ui/**",
        "/v3/**",
         "/swagger-ui.html/**"

        
    );



    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder encryptPasswordUser() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(urls.toArray(new String[0])).permitAll()
            /* 
            .requestMatchers("/login").permitAll()
            .requestMatchers("/Register/App/User").permitAll()
            .requestMatchers("/Authentication/SuccessfulAuthentication").permitAll()
            .requestMatchers("/Register/Google/User").permitAll()
            .requestMatchers("/Login/Google/User").permitAll()
            .requestMatchers("/oauth2/authorization/**").permitAll()
            */
            .anyRequest().authenticated())

            // In this part is to validate the Login in facebook or Google
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint // I get the information of the user from Google
                                .userService(customOAuth2UserService)).successHandler(redirect)) // Here I pass the service charge save the
                                                                        // object user in the session or general context
                // Here I add new filter to make my logic, because the user and the has been authenticated
                //.addFilter(new CustomTokenValidationFilter(authenticationManager(), clientService))
                //.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
     //   .addFilter(new AuthenticationStart(authenticationManager(), buildToken, loginGeneral, repósitory, messagesFinal,generateResponse))
        .addFilter(new ValidateToken(authenticationManager(), tokenService2, tokenService2))


        //This part is to handle about the logout
        .logout(logout ->
                        logout.logoutUrl("/AdiosApp") // Here I specify the page to logout and I add the logout handler
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
    //.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Hability the config to accept one
                                                                                   // frontedn in my backend
        .csrf(config -> config.disable())
        .build();
    }

    // C:\Maven\apache-maven-3.8.6\bin).
    // In this method I ALLOW PERMISSION TO FRONTEND TO CONNECT WIYTH MY BACKEND AND
    // HE CAN DO SOME ACTION(GET,POST,DELETE,PUT)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("http://127.0.0.1:5500")); // // Allow all origins
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT")); // Here i specify the type of the
                                                                                 // allowed to query
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));// This specifies which HTTP headers
                                                                                 // are allowed in the request. Here, it
                                                                                 // allows Authorization and
                                                                                 // Content-Type headers. This is
                                                                                 // important for allowing JWT tokens to
                                                                                 // be sent in the Authorization header.
        config.setAllowCredentials(true);// This indicates whether the browser should include any credentials (such as
                                         // cookies, authorization headers, or TLS client certificates) with the
                                         // requests to your API.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // // Apply to all URLs
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }

}
