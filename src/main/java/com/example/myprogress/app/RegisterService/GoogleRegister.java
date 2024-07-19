package com.example.myprogress.app.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Repositories.GoogleUserRepository;

public final class GoogleRegister extends Register  {

    @Autowired
    private GoogleUserRepository googleUserRepository;
    @Override
    public boolean validateEmail(String email, String typeAuthentication) {
        return googleUserRepository.ExistEmail(email, typeAuthentication);
    }

    @Override
    public boolean validateUser(String user, String typeAuthentication) {
        return !googleUserRepository.ExistUser(user, typeAuthentication);
    }

    @Override
    public boolean logerUser(String user, String passWord, String email,String typeAuthentication) {
       if (!googleUserRepository.addUser(user, passWord,email, typeAuthentication)){
            new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception 
            return false;
        }
        return true; // return true if the user was registered
    }
    
    @Override
    public <T> T getInformationUser(String idUser, String typeAuthentication) {
        return (T) googleUserRepository.getUserSelected(idUser, typeAuthentication);
    }

}
