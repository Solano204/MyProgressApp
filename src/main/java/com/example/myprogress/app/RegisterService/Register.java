package com.example.myprogress.app.RegisterService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.EmailExistException;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Exceptions.UserExistException;
import com.example.myprogress.app.GeneralServices.GeneratorDataUser;
import com.example.myprogress.app.GeneralServices.MessagesFinal;

// In this example, the Register interface is sealed. and I'll apply the design pattern TEMPLATE
@Service
public sealed abstract class Register permits FacebookRegister, GoogleRegister, AppRegister  {


    private GeneratorDataUser generatorDataUser;
    private MessagesFinal messagesFinal;
    private Map<String, Object> messages;

    // This is a default method and the template that all the implementations must
    // follow
    public boolean templateRegister(appUser user) {
        messages = new HashMap<>();
        // if the email exist I log the user automatically with existing account
        if (validateEmail(user.getEmail(), user.getTypeAuthentication())) {
            // I fill the success information
            throw new EmailExistException("The email is already in use");
        }

        // The false is meant the user exist and happen an error
        if (!validateUser(user.getUser())) {
            throw new UserExistException("The user Already exists");
        }

        // Here I'll do the register
        if (logerUser(user)) {
            return true;
        }
        return true;
    }

    public boolean logerUserExist() {
        return true;
    }




    // return true if the email exists and was succesful the Register or the
    // register
    abstract boolean validateEmail(String email, String typeAuthentication);

    // Return false if the user exists and was unsuccesful the Register or the
    // register
    abstract boolean validateUser(String user);

    abstract boolean logerUser(appUser user);


    // This class will general For all type of the register(Facebook,Google,App)
    public void loadRecommendedData(User user){
        generatorDataUser.setCurrentUser(user);
        generatorDataUser.getregisterInfo();
        generatorDataUser.generateCaloriesRecommended();
        generatorDataUser.generateMacrosnutrientsRecommended();
        generatorDataUser.generateCurrentStateHealth();
        generatorDataUser.updateInformationRecommended();
    }

    public GeneratorDataUser getGeneratorDataUser() {
        return generatorDataUser;
    }

    public void setGeneratorDataUser(GeneratorDataUser generatorDataUser) {
        this.generatorDataUser = generatorDataUser;
    }
    

}
