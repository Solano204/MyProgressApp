package com.example.myprogress.app.GeneralServices;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.infoLogged;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

// In this class will generate The information recommended in order to the user achieve its goal 

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Setter
@Getter
@Service
public class GeneratorDataUser {

    User currentUser;
    InfoRegister registerInfo;
    infoLogged infoLoged;

    // Here I initialize this class to easily the get the information of the user
    public void getregisterInfo() {
        registerInfo = currentUser.getRegisterInformation();
        infoLoged = currentUser.getInfoLogged();
    }

    // In this method I generate the calories recommended
    public double generateCaloriesRecommended() {
        double normalCalories = switch (registerInfo.getGender()) {
            case "Masculino" -> {
                yield (10 * registerInfo.getCurrentWeight() + 6.25 * (registerInfo.getHeight() * 100)
                        - 5 * registerInfo.getAge() + 5) * registerInfo.getValueActivity();
            }
            case "Femenino" -> {
                yield (10 * registerInfo.getCurrentWeight() + 6.25 * (registerInfo.getHeight() * 100)
                        - 5 * registerInfo.getAge() - 161) * registerInfo.getValueActivity();
            }

            default -> (10 * registerInfo.getCurrentWeight() + 6.25 * (registerInfo.getHeight() * 100)
                    - 5 * registerInfo.getAge() + 5) * registerInfo.getValueActivity();
                };

        double caloriesRecommended = switch (registerInfo.getGoal()) {
            case "Perder Peso" -> {
                yield (normalCalories - 300);
            }
            case "Mantener Peso" -> {
                yield (normalCalories);
            }

            case "Ganar Peso" -> {
                yield (normalCalories + 300);
            }
            default -> (10 * registerInfo.getCurrentWeight() + 6.25 * (registerInfo.getHeight() * 100)
                    - 5 * registerInfo.getAge() + 5) * registerInfo.getValueActivity();
        };
        infoLoged.setCurrentCalories((int) caloriesRecommended);
        return caloriesRecommended;
    }

    // In this method I generate the macros recommend according the user's goal
    public void generateMacrosnutrientsRecommended() {
        switch (registerInfo.getGoal()) {
            case "Perder Peso": { // 30 protein, 40 car,30 fats
                infoLoged.setCurrentProtein((int) ((infoLoged.getCurrentCalories() * 0.30) / 4));
                infoLoged.setCurrentCarbohydrates((int) ((infoLoged.getCurrentCalories() * 0.40) / 4));
                infoLoged.setCurrentFats((int) ((infoLoged.getCurrentCalories() * 0.39) / 9));
            }
            case "Mantener Peso": { // 25 pro, 50 car, 25 fats
                infoLoged.setCurrentProtein((int) ((infoLoged.getCurrentCalories() * 0.25) / 4));
                infoLoged.setCurrentCarbohydrates((int) ((infoLoged.getCurrentCalories() * 0.50) / 4));
                infoLoged.setCurrentFats((int) ((infoLoged.getCurrentCalories() * 0.25) / 9));
            }
            case "Ganar Peso": { // 20 pro, 55 car, 25 fats
                infoLoged.setCurrentProtein((int) ((infoLoged.getCurrentCalories() * 0.20) / 4));
                infoLoged.setCurrentCarbohydrates((int) ((infoLoged.getCurrentCalories() * 0.55) / 4));
                infoLoged.setCurrentFats((int) ((infoLoged.getCurrentCalories() * 0.25) / 9));
            }

        }
        ;
    }

    // In this i calculate user's state health
    public void generateCurrentStateHealth() {
        double bmi = (registerInfo.getCurrentWeight() / (registerInfo.getHeight() * registerInfo.getHeight()));
        String statuHealth = "";
        if (bmi < 18.5) {
            statuHealth = "Bajo de peso";
        }
        if (bmi < 24.9) {
            statuHealth = "Peso Normal";
        }
        if (bmi < 29.5) {
            statuHealth = "Pasado de peso";
        }
        infoLoged.setStateHealth(statuHealth);
    };

    public void calculateTheGainedAndLostWeight(int caloriesConsumed) {
        double caloriasRecommended = generateCaloriesRecommended();
        // Here I calculate the gained or lost weight
        double weightLostOrGained = (caloriesConsumed-caloriasRecommended)/7700;
        infoLoged.setCurrentWeight(registerInfo.getCurrentWeight() + (weightLostOrGained)); // Here I update the weight with add the gained or lost weight
        registerInfo.setCurrentWeight(registerInfo.getCurrentWeight() + (weightLostOrGained)); 
        

        //Here I define if the gained or lost weight 
        if (registerInfo.getCurrentWeight() > registerInfo.getStartingWeight ()) { // if The current weight is greater than the Starting weight it means that gained weight
            infoLoged.setGainedWeight(registerInfo.getCurrentWeight() - registerInfo.getStartingWeight());// I update the gained weight (compare the current weight with the starting weight)
            infoLoged.setLostWeight(0); // I update the lost weight in cero because I can have a amount of gained weight and the same time a amount of lost weight
        }else if(registerInfo.getCurrentWeight() == registerInfo.getStartingWeight ()) { // if The current weight is equal to the Starting weight it means didnt gained weight
            infoLoged.setGainedWeight(0);
            infoLoged.setLostWeight(0);
        }else{
            infoLoged.setLostWeight(registerInfo.getStartingWeight() - registerInfo.getCurrentWeight());
            infoLoged.setGainedWeight(0);
        }
        //Here I
    }

    public void updateInformationRecommended() {
        infoLoged.setStartingDate(LocalDate.now());
        currentUser.setRegisterInformation(registerInfo);
        currentUser.setInfoLogged(infoLoged);
    }

}
