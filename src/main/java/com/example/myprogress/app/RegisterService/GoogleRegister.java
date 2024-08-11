package com.example.myprogress.app.RegisterService;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Repositories.GoogleUserRepository;

public final class GoogleRegister extends Register  implements Serializable {

    private GoogleUserRepository googleUserRepository;

    public GoogleRegister(GoogleUserRepository googleUserRepository) {
        this.googleUserRepository = googleUserRepository;
    }

    @Override
    public boolean validateEmail(String email, String typeAuthentication) {
        return googleUserRepository.ExistEmail(email, typeAuthentication);
    }

    @Override
    public boolean validateUser(String user) {
        return !googleUserRepository.ExistUser(user);
    }

    @Override
    public boolean logerUser(appUser user) {
       if (googleUserRepository.addUser(user.getUser(), "null",user.getEmail(), user.getTypeAuthentication())){
           loadRecommendedData(user);
           if (googleUserRepository.addProgressUser(user)){
               return true;   
            } 
            throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
        }
        throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
    }


}
