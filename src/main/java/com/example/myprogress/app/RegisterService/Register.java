package com.example.myprogress.app.RegisterService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Exceptions.UserExistException;

// In this example, the Register interface is sealed. and I'll apply the design pattern TEMPLATE
@Service
public sealed abstract class Register permits FacebookRegister, GoogleRegister, AppRegister {
    Map<String, Object> messages;

    // This is a default method and the template that all the implementations must
    // follow
    public boolean templateRegister(final String user, final String passWord, final String email,
            String typeAuthentication) {
        messages = new HashMap<>();
        // if the email exist I log the user automatically with existing account
        if (validateEmail(email,typeAuthentication)) {
            // I fill the success information
            fillInformationSuccess(user, typeAuthentication);
            return true;
        }

        // The false is meant the user exist and happen an error
        if (!validateUser(user,typeAuthentication)) {
            throw new UserExistException ("The user Already exists");
        }
        
        // Here I'll do  the register
        if(logerUser(user,passWord,email,typeAuthentication)){
            fillInformationSuccess(user, typeAuthentication);
            return true;
        }

        return true;
    }

    public boolean logerUserExist() {
        return true;
    }

    // This method will be used to fill with the information of the success register
    // and the user registered
    public <T> void fillInformationSuccess(final String idUser, final String typeAuthentication) {

        // If the typeAuthentication is AppRegister I get the password
        if (typeAuthentication.equals("AppRegister")) {
            appUser appUser = getInformationUser(idUser, typeAuthentication);
            messages.put("password", appUser.getPassWord());
            fillMapInformation(messages, appUser);
            return;
        }
        User user = getInformationUser(idUser, typeAuthentication);
        fillMapInformation(messages, user);
    }

    public void fillMapInformation(Map messages,User user){
        messages.put("user", user.getUser());
        messages.put("email", user.getEmail());
        messages.put("typeAuthentication", user.getTypeAuthentication());
        messages.put("currentStarting", user.getCurrentStarting());
        messages.put("startingWeight", user.getStartingWeight());
        messages.put("currentWeight", user.getCurrentWeight());
        messages.put("endWeight", user.getEndWeight());
        messages.put("currentCalories", user.getCurrentCalories());
        messages.put("lostWeight", user.getLostWeight());
        messages.put("gainedWeight", user.getGainedWeight());
        messages.put("success", "The user was successfully register");

    }
    // return true if the email exists and was succesful the Register or the
    // register
    abstract boolean validateEmail(String email, String typeAuthentication);

    // Return false if the user exists and was unsuccesful the Register or the
    // register
    abstract boolean validateUser(String user, String typeAuthentication);

    abstract boolean logerUser(String user, String passWord, String passwordConfirm, String typeAuthentication);

    abstract <T> T getInformationUser(String idUser, String typeAuthentication);

    public Map<String, Object> getMessages() {
        return messages;
    }public void setMessages(Map<String, Object> messages) {
        this.messages = messages;
    }
}
