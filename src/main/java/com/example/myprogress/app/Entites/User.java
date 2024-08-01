package com.example.myprogress.app.Entites;

import java.time.LocalDate;

import com.example.myprogress.app.validations.AuthenticationUser;
import com.example.myprogress.app.validations.RegisterInformation;
import com.example.myprogress.app.validations.ValidationOnlyRegisterGroup;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;



@Data
@FieldDefaults(level = lombok.AccessLevel.PROTECTED) 

// In this classs will apply the inheritance between classes

@MappedSuperclass
public sealed class User permits appUser, faceUser, googleUser {

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

    @Id
    @Column(name = "id_user")
    String user;
    
    @Email(message = "El email no es valido")
    @Column(name = "email_user")
    String email;

    @Column(name = "type_authentication")
    String typeAuthentication;

     // This is the information when the user will be registered 
    @Transient
    @RegisterInformation(groups = {ValidationOnlyRegisterGroup.class})
    InfoRegister registerInformation;

    // This is the information when the new user was registered
    @Transient
    infoLogged infoLogged;




    
}
