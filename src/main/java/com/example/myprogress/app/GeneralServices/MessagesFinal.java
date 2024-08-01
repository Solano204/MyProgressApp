package com.example.myprogress.app.GeneralServices;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.infoLogged;


@Component
public class MessagesFinal {

    public void fillMapInformation(Map messages, User user) {
        InfoRegister registerInfo = user.getRegisterInformation();
        infoLogged loggedInfo = user.getInfoLogged();

        messages.put("User", user.getUser());
        messages.put("Email", user.getEmail());
        messages.put("TypeAuthentication", user.getTypeAuthentication());
        messages.put("NameUser", registerInfo.getName());
        messages.put("Country", registerInfo.getCountry());
        messages.put("Height", registerInfo.getHeight());
        messages.put("Age", registerInfo.getAge());
        messages.put("DateStarting", loggedInfo.getStartingDate());
        messages.put("StartingWeight", registerInfo.getStartingWeight());
        messages.put("CurrentWeight", loggedInfo.getCurrentWeight());
        messages.put("EndWeight", registerInfo.getEndWeight());
        messages.put("LostWeight", loggedInfo.getLostWeight());
        messages.put("GainedWeight", loggedInfo.getGainedWeight());
        messages.put("CurrentCalories", loggedInfo.getCurrentCalories());
        messages.put("Protein", loggedInfo.getCurrentProtein());
        messages.put("Carbohydrates", loggedInfo.getCurrentCarbohydrates());
        messages.put("Fat", loggedInfo.getCurrentFats());
        messages.put("Goal", registerInfo.getGoal());
        messages.put("Gender", registerInfo.getGender());
        messages.put("StateHealth", loggedInfo.getStateHealth());
        messages.put("TypeLevel", registerInfo.getLevelActivity());
        messages.put("success", "The user was successfully register");

    }
}
