package com.example.myprogress.app.RegisterService;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myprogress.app.Entites.User;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Repositories.FaceUserRepository;

public final class FacebookRegister extends Register implements Serializable {

    private FaceUserRepository facebookUserRepository;

    public FacebookRegister(FaceUserRepository facebookUserRepository) {
        this.facebookUserRepository = facebookUserRepository;
    }

    @Override
    public boolean validateEmail(String email, String typeAuthentication) {
        return facebookUserRepository.ExistEmail(email, typeAuthentication);
    }

    @Override
    public boolean validateUser(String user) {
        return !facebookUserRepository.ExistUser(user);
    }

    @Override
    public boolean logerUser(appUser user) {
        if (facebookUserRepository.addUser(user.getUser(), "null", user.getEmail(), user.getTypeAuthentication())) {
            loadRecommendedData(user);
            if (facebookUserRepository.addProgressUser(user)) {
                return true;
            }
            throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
        }
        throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
    }

}
