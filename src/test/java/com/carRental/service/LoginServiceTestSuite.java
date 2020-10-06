package com.carRental.service;

import com.carRental.domain.Login;
import com.carRental.exceptions.LoginNotFoundException;
import com.carRental.repository.LoginRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTestSuite {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private LoginRepository loginRepository;

    @Test
    public void isLoginRegisteredTest() {
        //Given
        when(loginRepository.existsByEmailAndPassword("email", "password")).thenReturn(true);

        //When
        boolean result = loginService.isLoginRegistered("email", "password");

        assertTrue(result);
    }

    @Test
    public void getLoginByEmailAndPasswordTest() throws LoginNotFoundException {
        //Given
        Login login = initLogin();
        when(loginRepository.findByEmailAndPassword("email", "password")).thenReturn(Optional.of(login));

        //When
        Login result = loginService.getLoginByEmailAndPassword("email", "password");

        //Then
        assertEquals("email", result.getEmail());
        assertEquals("password", result.getPassword());
    }

    @Test
    public void saveLoginTest() {
        //Given
        Login login = initLogin();

        //When
        loginService.saveLogin(login);

        //Then
        verify(loginRepository, times(1)).save(login);
    }

    @Test
    public void deleteLoginTest() {
        //Given
        Login login = initLogin();

        //When
        loginService.deleteLogin(login);

        //Then
        verify(loginRepository, times(1)).delete(login);
    }

    private Login initLogin() {
        return new Login("email", "password");
    }
}
