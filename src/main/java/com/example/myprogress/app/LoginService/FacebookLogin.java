package com.example.myprogress.app.LoginService;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Repositories.FaceUserRepository;

public non-sealed class FacebookLogin extends Login {

    private FaceUserRepository facebookUserRepository;

    public FacebookLogin(FaceUserRepository facebookUserRepository) {
        this.facebookUserRepository = facebookUserRepository;
    }

    @Override
    public boolean validateEmail(String email, String typeAuthentication) {
        return facebookUserRepository.ExistEmail(email, typeAuthentication);
    }

    @Override
    public boolean validateUser(String user, String password) {
        return facebookUserRepository.ExistUser(user);
    }


}
