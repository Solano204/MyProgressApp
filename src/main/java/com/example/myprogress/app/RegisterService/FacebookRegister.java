package com.example.myprogress.app.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Repositories.FaceUserRepository;

public final class FacebookRegister extends Register  {


    
    private  FaceUserRepository facebookUserRepository;


    
    public FacebookRegister(FaceUserRepository facebookUserRepository) {
        this.facebookUserRepository = facebookUserRepository;
    }

    @Override
    public boolean validateEmail(String email,String typeAuthentication) {
        return facebookUserRepository.ExistEmail(email,typeAuthentication);
    }

    @Override
    public boolean validateUser(String user,String typeAuthentication) {
        return !facebookUserRepository.ExistUser(user, typeAuthentication);
    }

    @Override
    public boolean logerUser(String user, String passWord, String email,String typeAuthentication) {
        if (!facebookUserRepository.addUser(user,passWord, email,typeAuthentication)) {
            new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception 
            return false;
        }
        return true; // return true if the user was registered
    }

    @Override
    public <T> T getInformationUser(String idUser, String typeAuthentication) {
        return (T) facebookUserRepository.getUserSelected(idUser, typeAuthentication);
    }

}
