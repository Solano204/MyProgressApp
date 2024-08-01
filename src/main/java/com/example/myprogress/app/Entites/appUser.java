package com.example.myprogress.app.Entites;

import java.time.LocalDate;

import com.example.myprogress.app.validations.AuthenticationUser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@AuthenticationUser
@Table(name = "App_Users")
public final class appUser extends User {
    @Column(name = "Password")
    private String passWord;



    // This contructor will be used in the most of the case to create a new user
    public appUser(String idUser, String password, String email, String typeAuthentication) {
        super(idUser, email, typeAuthentication);
        this.passWord = password;
    }

    // This constructor will be used to map the response of the SqlResultSetMapping

    public appUser(String user, String email, String password,String typeAuthentication, LocalDate currentStarting, double StartingWeight,
    double CurrentWeight, double EndWeight, int CurrentCalories, double LostWeight, double gainedWeight) {
        super(user, email,typeAuthentication, currentStarting, StartingWeight, CurrentWeight, EndWeight, CurrentCalories, LostWeight, gainedWeight);
        this.passWord = password;
    }   


}