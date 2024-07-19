package com.example.myprogress.app.Entites;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

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
        public User(String user, String email, String typeAuthentication, LocalDate currentStarting, int StartingWeight,
                int CurrentWeight, int EndWeight, int CurrentCalories, int LostWeight, int gainedWeight) {
            this.user = user;
            this.email = email;
            this.typeAuthentication = typeAuthentication;
            this.currentStarting = currentStarting;
            this.StartingWeight = StartingWeight;
            this.CurrentWeight = CurrentWeight;
            this.EndWeight = EndWeight;
            this.CurrentCalories = CurrentCalories;
            this.LostWeight = LostWeight;
            this.gainedWeight = gainedWeight;
        }   

    public User() {
    }

    @Id
    @Column(name = "id_user")
    protected String user;
    @Column(name = "email_user")
    protected String email;
    @Column(name = "type_authentication")
    protected String typeAuthentication;

    @Transient
    protected LocalDate currentStarting;
    @Transient
    protected int StartingWeight;
    @Transient
    protected int CurrentWeight;
    @Transient
    protected int EndWeight;
    @Transient
    protected int CurrentCalories;
    @Transient
    protected int LostWeight;
    @Transient
    protected int gainedWeight;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeAuthentication() {
        return typeAuthentication;
    }

    public void setTypeAuthentication(String typeAuthentication) {
        this.typeAuthentication = typeAuthentication;
    }

    public LocalDate getCurrentStarting() {
        return currentStarting;
    }

    public void setCurrentStarting(LocalDate currentStarting) {
        this.currentStarting = currentStarting;
    }

    public int getStartingWeight() {
        return StartingWeight;
    }

    public void setStartingWeight(int startingWeight) {
        StartingWeight = startingWeight;
    }

    public int getCurrentWeight() {
        return CurrentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        CurrentWeight = currentWeight;
    }

    public int getEndWeight() {
        return EndWeight;
    }

    public void setEndWeight(int endWeight) {
        EndWeight = endWeight;
    }

    public int getCurrentCalories() {
        return CurrentCalories;
    }

    public void setCurrentCalories(int currentCalories) {
        CurrentCalories = currentCalories;
    }

    public int getLostWeight() {
        return LostWeight;
    }

    public void setLostWeight(int lostWeight) {
        LostWeight = lostWeight;
    }

    public int getGainedWeight() {
        return gainedWeight;
    }

    public void setGainedWeight(int gainedWeight) {
        this.gainedWeight = gainedWeight;
    }



    
}
