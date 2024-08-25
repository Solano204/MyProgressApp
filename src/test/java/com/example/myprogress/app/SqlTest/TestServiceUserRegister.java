package com.example.myprogress.app.SqlTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.EmailExistException;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.Exceptions.UserExistException;
import com.example.myprogress.app.RegisterService.AppRegister;
import com.example.myprogress.app.Repositories.AppUserRepository;


// HERE I TEST THE PROCESS OF THE REGISTRATION NEW USER
@SpringBootTest
class TestServiceUserRegister {
   @MockBean
    private AppUserRepository appUserRepository;

    @InjectMocks
    @Spy
    private AppRegister appRegister;

    private appUser user;

    @BeforeEach
    public void setUp() {
        user = new appUser("user1", "password", "email@example.com", "App");
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void UnsuccessfulValidateEmailException() {
        when(appUserRepository.ExistUser(anyString())).thenReturn(false);
        when(appUserRepository.ExistEmail(anyString(), anyString())).thenReturn(true);

        Exception ex = assertThrows(EmailExistException.class, () -> appRegister.templateRegister(user));

        assertAll(
            () -> assertEquals("The email is already in use", ex.getMessage()),
            () -> verify(appUserRepository, never()).addUser(anyString(), anyString(), anyString(), anyString()),
            () -> verify(appUserRepository, never()).addProgressUser(any(appUser.class))
        );
    }

    @Test
    void UnsuccessfulValidateUserException() {
        when(appUserRepository.ExistUser(anyString())).thenReturn(true);
        when(appUserRepository.ExistEmail(anyString(), anyString())).thenReturn(false);

        Exception ex = assertThrows(UserExistException.class, () -> appRegister.templateRegister(user));

        assertAll(
            () -> assertEquals("The user Already exists", ex.getMessage()),
            () -> verify(appUserRepository, never()).addUser(anyString(), anyString(), anyString(), anyString()),
            () -> verify(appUserRepository, never()).addProgressUser(any(appUser.class)),
            () -> assertEquals("App", user.getTypeAuthentication(), "Type of authentication should be 'App'")
        );
    }

    @Test
    void unsuccesfulAddUserException() {
        when(appUserRepository.addUser(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        when(appUserRepository.addProgressUser(user)).thenReturn(true);

        Exception ex = assertThrows(UnsuccessfulRegisterException.class, () -> appRegister.logerUser(user));

        assertAll(
            () -> assertEquals("The user couldn't be registered", ex.getMessage()),
            () -> verify(appUserRepository).addUser(anyString(), anyString(), anyString(), anyString()),
            () -> verify(appUserRepository, never()).addProgressUser(user),
            () -> verify(appRegister, never()).loadRecommendedData(any(appUser.class))
        );
    }

    @Test
    void unsuccesfulAddProgressException() {
        doNothing().when(appRegister).loadRecommendedData(any(appUser.class));
        when(appUserRepository.addUser(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        when(appUserRepository.addProgressUser(user)).thenReturn(false);

        Exception ex = assertThrows(UnsuccessfulRegisterException.class, () -> appRegister.logerUser(user));

        assertAll(
            () -> assertEquals("The user couldn't be registered", ex.getMessage()),
            () -> verify(appUserRepository).addUser(anyString(), anyString(), anyString(), anyString()),
            () -> verify(appUserRepository).addProgressUser(user),
            () -> verify(appRegister).loadRecommendedData(any(appUser.class))
        );
    }

    @Test
    void successfulEntireRegisterWithoutException() {
        when(appUserRepository.ExistUser(anyString())).thenReturn(false);
        when(appUserRepository.ExistEmail(anyString(), anyString())).thenReturn(false);
        when(appUserRepository.addUser(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        when(appUserRepository.addProgressUser(user)).thenReturn(true);
        doNothing().when(appRegister).loadRecommendedData(any(appUser.class)); // Here mock the method of the service

        assertDoesNotThrow(() -> appRegister.templateRegister(user));
        assertAll(
            () -> verify(appUserRepository).ExistUser(user.getUser()),
            () -> verify(appUserRepository).ExistEmail(user.getEmail(), user.getTypeAuthentication()),
            () -> verify(appUserRepository).addUser(user.getUser(), user.getPassWord(), user.getEmail(), user.getTypeAuthentication()),
            () -> verify(appUserRepository).addProgressUser(user),
            () -> verify(appRegister).loadRecommendedData(user)
        );
    }
}
