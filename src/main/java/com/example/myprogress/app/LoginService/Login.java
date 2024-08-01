package com.example.myprogress.app.LoginService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.EmailExistException;
import com.example.myprogress.app.Exceptions.PasswordIncorrect;
import com.example.myprogress.app.Exceptions.UserExistException;
import com.example.myprogress.app.GeneralServices.GeneratorDataUser;
import com.example.myprogress.app.GeneralServices.MessagesFinal;

@Service
public sealed abstract class Login permits AppLogin, FacebookLogin, GoogleLogin {

    private MessagesFinal messagesFinal;
    private  Map<String, Object> messages;

    // This is a default method and the template that all the implementations must
    // follow
    public boolean templateLogin(appUser user) {
        messages = new HashMap<>();
        // if false the it means the email not exist and happen an error
        if (!validateEmail(user.getEmail(), user.getTypeAuthentication())) {
            // I fill the success information
            throw new EmailExistException("The email Not found , please register");
        }
        // The false is meant the user not exist and happen an error
        if (!validateUser(user.getUser(),user.getPassWord())) {
            throw new UserExistException("The user Not found, please register");
        }
        // Here I'll do the login the user and get the data registered previous in its old or exiting login or account 
        fillInformationSuccess(user.getUser(), user.getTypeAuthentication());
        return true;
    }

    // This method will be used to fill with the information of the success register
    // and the user registered
    public <T> void fillInformationSuccess(final String idUser, final String typeAuthentication) {
        // If the typeAuthentication is AppRegister I get the password
        if (typeAuthentication.equals("App")) {
            appUser appUser = getInformationUser(idUser, typeAuthentication);
            messages.put("password", appUser.getPassWord());
            messagesFinal.fillMapInformation(messages, appUser);
            return;
        }
        User user = getInformationUser(idUser, typeAuthentication);
        messagesFinal.fillMapInformation(messages, user);
    }

    // Here I load all information about the new user registered in a map to send
    // the frontend

    // return false if the email Not exists and was succesful the Register or the
    // register
    abstract boolean validateEmail(String email, String typeAuthentication);
    // Return false if the user Not exist and was unsuccesful the Register or the
    // register
    abstract boolean validateUser(String user, String password);
    
    // Return false if the password is incorrect

    abstract <T> T getInformationUser(String idUser, String typeAuthentication);

    public Map<String, Object> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Object> messages) {
        this.messages = messages;
    }

    public void setMessagesFinal(MessagesFinal messagesFinal) {
        this.messagesFinal = messagesFinal;
    }

    public MessagesFinal getMessagesFinal() {
        return messagesFinal;
    }
}
