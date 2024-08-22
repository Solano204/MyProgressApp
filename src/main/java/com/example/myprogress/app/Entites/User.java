package com.example.myprogress.app.Entites;

import java.io.Serializable;
import java.time.LocalDate;

import com.example.myprogress.app.validations.AuthenticationUser;
import com.example.myprogress.app.validations.RegisterInformation;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;



@Data
@Getter
@Setter

// In this classs will apply the inheritance between classes

@MappedSuperclass
@Schema(description = "User entity representing a registered user in the system")   
public abstract class User implements Serializable {

    // This is the constructor of the class to do transfer between java and sql
    public User(String user, String email, String typeAuthentication) {
        this.user = user;
        this.email = email;
        this.typeAuthentication = typeAuthentication;
    }

    // This is the constructor will help the subclasses to can use the class
    // @SqlResultSetMapping,This will be used by the namedQuery
        public User(String user, String email, String typeAuthentication, LocalDate currentStarting, double StartingWeight,
                double CurrentWeight, double EndWeight, int CurrentCalories, double LostWeight, double gainedWeight) {
            this.user = user;
            this.email = email;
            this.typeAuthentication = typeAuthentication;
        }   

    public User() {
    }


    @Schema(description = "Unique identifier of the user", 
            example = "younowjs2", 
            required = true)
    @Id
    @Column(name = "id_user")
    String user;
    

    @Schema(description = "Email address of the user", 
    example = "tuEmail@lsmes.com", 
    required = true)
    @Email(message = "El email no es valido")
    @Column(name = "email_user")
    String email;


    @Schema(description = "Type of authentication used by the user (e.g., App, Google)", 
    example = "App", 
    allowableValues = {"App", "Google"}, 
    required = true)
    @Transient
    String typeAuthentication;

    @Hidden
    @Column(name = "id_authentication")
    int auth;

     // This is the information when the user will be registered 
    @Transient
    
    InfoRegister registerInformation;

    // This is the information when the new user was registered
    @Transient
    InfosLogged infoLogged;




    
}
