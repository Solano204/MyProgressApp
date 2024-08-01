package com.example.myprogress.app.LoginService;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Repositories.GoogleUserRepository;

public non-sealed class GoogleLogin extends Login {


      private GoogleUserRepository googleUserRepository;

    public GoogleLogin(GoogleUserRepository googleUserRepository) {
        this.googleUserRepository = googleUserRepository;
    }

    @Override
    public boolean validateEmail(String email, String typeAuthentication) {
        return googleUserRepository.ExistEmail(email, typeAuthentication);
    }

    @Override
    public boolean validateUser(String user,String password) {
        return googleUserRepository.ExistUser(user);
    }
    
    
    @Override
    public <T> T getInformationUser(String idUser, String typeAuthentication) {
        return (T) googleUserRepository.getUserSelected(idUser, typeAuthentication);
    }


}
