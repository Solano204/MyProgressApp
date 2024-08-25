package com.example.myprogress.app.SqlTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.UserExistException;
import com.example.myprogress.app.Repositories.AppUserRepository;


// HERE I TEST SOME METHOD RELATED TO THE DATA BASE (Password,User)
@SpringBootTest
class ServicesDataUserTest {

        @Mock
        private AppUserRepository appUserRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @InjectMocks
        private com.example.myprogress.app.updateInformationService.updateInformationUserService updateInformationUserService;

        appUser user = appUser.builder().passWord("password").build();

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            user.setTypeAuthentication("App");
            user.setUser("existingUser");
        }

       


        @Test
        void testChangeUser_NewUserAlreadyExists() {
            when(appUserRepository.ExistUser("newUser")).thenReturn(true); // Here I modify the response of the first
                                                                           // call to this method
            assertThrows(UserExistException.class, () -> updateInformationUserService.changeUser(user, "newUser"));
            verify(appUserRepository, never()).changeUser(any(), anyString());

            Exception exception = assertThrows(UserExistException.class, () -> {
                updateInformationUserService.changeUser(user, "newUser");
            });
            String actual = exception.getMessage();
            String esperado = "Already exits a user with this user, please choose another one";
            assertEquals(esperado, actual);
        }

        @Test
        void testChangeUser_UserToChangeNotAlreadyExists() {
            when(appUserRepository.ExistUser("newUser")).thenReturn(false); // Here I modify the response of the first
                                                                            // call to this method
            when(appUserRepository.ExistUser(user.getUser())).thenReturn(false); // Here I modify the response of the
                                                                                 // second call to this method
            assertThrows(UserExistException.class, () -> updateInformationUserService.changeUser(user, "newUser"));
            verify(appUserRepository, never()).changeUser(any(), anyString());
            Exception exception = assertThrows(UserExistException.class, () -> {
                updateInformationUserService.changeUser(user, "User incorrect please check its the user, not exists");
            });
            String actual = exception.getMessage();
            String esperado = "User incorrect please check its the user, not exists";
            assertEquals(esperado, actual);
        }


        @Test
        void testChangePassword_Unsuccess() {
            String newPass = "newPassword";
            String oldPass = "password";

            when(appUserRepository.ExistUser(user.getUser())).thenReturn(true);
            when(appUserRepository.findByIdUser(user.getUser())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(user.getPassWord(), newPass)).thenReturn(false); // Here i emulate that the passwords are incorrect
            when(appUserRepository.updatePassword(anyString(), eq(user.getUser()))).thenReturn(1);
            int result = updateInformationUserService.changePassword(user.getPassWord(), user.getUser(),  user.getPassWord());
            assertEquals(0, result);
            verify(appUserRepository, never()).updatePassword(anyString(), eq(user.getUser()));
        }


        @Test
        void testDeleteUser_WrongPassword() {
            when(appUserRepository.ExistUser(user.getUser())).thenReturn(true);
            when(appUserRepository.findByIdUser(user.getUser())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(user.getPassWord(), "correctPassword")).thenReturn(false);
            boolean result = updateInformationUserService.deleteUser(user);

            assertFalse(result);
            verify(appUserRepository, never()).deleteUser(user);
        }
    }
