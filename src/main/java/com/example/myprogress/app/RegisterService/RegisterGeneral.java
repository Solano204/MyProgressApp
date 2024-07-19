package com.example.myprogress.app.RegisterService;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.myprogress.app.Repositories.AppUserRepository;
import com.example.myprogress.app.Repositories.FaceUserRepository;

@Service
public class RegisterGeneral {

    private AppUserRepository appUserRepository;
    private FaceUserRepository faceUserRepository; 

    public RegisterGeneral(AppUserRepository appUserRepository, FaceUserRepository faceUserRepository) {
        this.appUserRepository = appUserRepository;
        this.faceUserRepository = faceUserRepository;
    }

    private Register register;

    
    public boolean RegisterUser(final String user, final String passWord,
            final String email, final String typeRegister) {
        return switch (typeRegister) {
            case "FacebookRegister" -> {
                register = new FacebookRegister(faceUserRepository);
                yield register.templateRegister(user, "NoPassword", email, typeRegister);
            }
            case "GoogleRegister" -> {
                register = new GoogleRegister();
                yield register.templateRegister(user, "NoPassword", email, typeRegister);
            }
            case "AppRegister" -> {
                register = new AppRegister(appUserRepository);
                yield register.templateRegister(user, passWord, email, typeRegister);
            }
            default -> false;
        };
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }
}
