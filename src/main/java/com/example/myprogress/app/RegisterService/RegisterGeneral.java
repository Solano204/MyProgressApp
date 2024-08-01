package com.example.myprogress.app.RegisterService;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.GeneralServices.GeneratorDataUser;
import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.Repositories.AppUserRepository;
import com.example.myprogress.app.Repositories.FaceUserRepository;
import com.example.myprogress.app.Repositories.GoogleUserRepository;

@Service
public class RegisterGeneral {

    private AppUserRepository appUserRepository;
    private FaceUserRepository faceUserRepository; 
    private GoogleUserRepository googleUserRepository;
    private GeneratorDataUser generatorDataUser;
    private MessagesFinal messagesFinal;

    public RegisterGeneral(AppUserRepository appUserRepository, FaceUserRepository faceUserRepository, GoogleUserRepository googleUserRepository, GeneratorDataUser generatorDataUser, MessagesFinal messagesFinal) {
        this.appUserRepository = appUserRepository;
        this.faceUserRepository = faceUserRepository;
        this.googleUserRepository = googleUserRepository;
        this.generatorDataUser = generatorDataUser;
        this.messagesFinal = messagesFinal;
    }

    private Register register;

    
    public boolean RegisterUser(appUser user) {
        return switch (user.getTypeAuthentication()) {
            case "Facebook" -> {
                
                register = new FacebookRegister(faceUserRepository);
                register.setGeneratorDataUser(generatorDataUser);
                register.setMessagesFinal(messagesFinal);
                yield register.templateRegister(user);

            }
            case "Google" -> {
                register = new GoogleRegister(googleUserRepository);
                register.setGeneratorDataUser(generatorDataUser);
                register.setMessagesFinal(messagesFinal);
                yield register.templateRegister(user);
            }
            case "App" -> {
                register = new AppRegister(appUserRepository);
                register.setGeneratorDataUser(generatorDataUser);
                register.setMessagesFinal(messagesFinal);
                yield register.templateRegister(user);
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
