package com.example.myprogress.app.SpringSecurity;


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// THIS CLASS IS TO VALIDATE IF THE TOKEN IS CORRECT
public class ValidateToken extends BasicAuthenticationFilter {

    public ValidateToken(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(VariablesGeneral.AUTHORIZATION);

        System.out.println("header " + header);

        if (header == null || !header.startsWith(VariablesGeneral.HEADER_TOKEN)) { // If the entity that is trygin query
                                                                                   // our backend and its public and
                                                                                   // doesnt have TOKEN, I direct it the
                                                                                   // query, This effectively means the user isn't authenticated.
            chain.doFilter(request, response); // This method help me to direct the user directly the method REQUEST
                                               // what He queried
            return;
        }

        // I remove the carrier, so I can only obtain the information from the token and
        // it can be evaluated
        String token = header.replace(VariablesGeneral.HEADER_TOKEN, "");
        System.out.println("token " + token);

        try {
            // THIS IS THE PROCCES OF THE AUTHENTICATION
            // The Jwts.parser() method initiates the parsing process. Here's what happens: Token Decomposition: The token is split into its three parts (header, payload, and signature)
            // VERIFIWITH, Here I get the token split and after using the secret key and the algorithm to validate if the key is valid, DONT VALIDATE THE USER AND ROLES,EXPIRITION only the KEY and also here calidate If the token is expired, jjwt will throw an ExpiredJwtException.If the token is expired, jjwt will throw an ExpiredJwtException. AUTOMATICALLY
            Claims claims = Jwts.parser().verifyWith(VariablesGeneral.SECRET_KEY).build().parseSignedClaims(token)
                    .getPayload(); // Verify if the token is correct with the Secret key,This part is very crucial is where Define if I following with the process
            
                    // Apart from here I wont need validate the user and authories, With the validation of the key is enough
            // String username = claims.getSubject(); TO GET THE USER 2 WAYS
            String username = (String) claims.get("username");
            Object authoritiesClaim = claims.get("authorities"); // I get the roles

            // To add the roles
            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                    // Registers ClassCasteoContructor as a mixin for
                    // SimpleGrantedAuthority.especifica que Jackson debe usar el constructor
                    // definido en ClassCasteoContructor para deserializar JSON en objetos
                    // SimpleGrantedAuthority.
                    .addMixIn(SimpleGrantedAuthority.class, ClassCasteoContructor.class)
                    /*
                     * Registers ClassCasteoContructor as a mixin for
                     * SimpleGrantedAuthority.especifica que Jackson debe usar el constructor
                     * definido en ClassCasteoContructor para deserializar JSON en objetos
                     * SimpleGrantedAuthority. lo que realmente hara es que si en el json hay un
                     * campo de nombre role [
                     * {"role": "ROLE_USER"},
                     * {"role": "ROLE_ADMIN"}
                     * and i have a class tranformara el nombre role a authority y este valor del json con el nombre role ahora sera guardado en el valor del objeto authority
                     * @JsonCreator
                     * public SimpleGrantedAuthority(@JsonProperty("role") String authority) {
                     * this.authority = authority;
                     * }
                     */

                    .readValue(authoritiesClaim.toString().getBytes(), SimpleGrantedAuthority[].class)); // This part
                                                                                                         // convert the
                                                                                                         // roles in a
                                                                                                         // array of the
                                                                                                         // byte and
                                                                                                         // after this
                                                                                                         // bytes will
                                                                                                         // to transform
                                                                                                         // or save in a
                                                                                                         // array type
                                                                                                         // SimpleGrantedAuthority[]

            // Here generate a token Tem where the user's information will be saved
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                    null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken); // This method saves the token with all the user information during the request, to make it easier for authorization checks and access control to work correctly. When the request ends, the context filter is removed.
            chain.doFilter(request, response); // I sent the next level or the next filter

        } catch (Exception e) {
            Map<String, String> body = new HashMap();
            body.put("error", e.getMessage());
            body.put("message", "Sorry your Token is invalid");
            body.put("error", e.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(VariablesGeneral.CONTENT_TYPE);
        }
    }

}
