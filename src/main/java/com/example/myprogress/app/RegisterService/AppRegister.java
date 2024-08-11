package com.example.myprogress.app.RegisterService;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Repositories.AppUserRepository;

public final class AppRegister extends Register implements Serializable {

    private AppUserRepository appUserRepository;

    public AppRegister(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public boolean validateEmail(String email, String typeAuthentication) {
        return appUserRepository.ExistEmail(email, typeAuthentication); // return true if the email exists
    }

    @Override
    public boolean validateUser(String user) {
        return !appUserRepository.ExistUser(user ); // Here I return the opposite (exits == false)
                                                                       // (does not exist == true)
    }

    @Override
    public boolean logerUser(appUser user) {
        if (appUserRepository.addUser(user.getUser(), user.getPassWord(), user.getEmail(),user.getTypeAuthentication())) {
            loadRecommendedData(user); // Here I call the method default to load the recommend to the current user
            if (appUserRepository.addProgressUser(user)) {
                return true;
            }
            throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
        }
        throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
    }


}
