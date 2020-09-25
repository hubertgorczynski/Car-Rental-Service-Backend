package com.carRental.service;

import com.carRental.domain.User;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.repository.UserRepository;
import com.carRental.service.emailService.EmailToUsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestSuite {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailToUsersService emailToUsersService;

    User user = new User(
            1L,
            "Jack",
            "Smith",
            "email",
            "password",
            123456);

    @Test
    public void saveUserTest() {
        //Given
        when(userRepository.save(user)).thenReturn(user);
        doNothing().when(emailToUsersService).sendEmailAboutCreatingUser(user);

        //When
        User result = userService.saveUser(user);

        //Then
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getPhoneNumber(), result.getPhoneNumber());
        verify(emailToUsersService, times(1)).sendEmailAboutCreatingUser(user);
    }

    @Test
    public void getUserByIdTest() throws UserNotFoundException {
        //Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //When
        User result = userService.getUserById(1L);

        //Then
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void getUserByEmailTest() throws UserNotFoundException {
        //Given
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));

        //When
        User result = userService.getUserByEmail("email");

        //Then
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    public void getUserByPhoneNumberTest() throws UserNotFoundException {
        //Given
        when(userRepository.findByPhoneNumber(123456)).thenReturn(Optional.of(user));

        //When
        User result = userService.getUserByPhoneNumber(123456);

        //Then
        assertEquals(user.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    public void getAllUsersTest() {
        //Given
        List<User> userList = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(userList);

        //When
        List<User> resultList = userService.getAllUsers();

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
    }

    @Test
    public void deleteUserTest() {
        //Given
        //When
        userService.deleteUser(2L);

        //Then
        verify(userRepository, times(1)).deleteById(2L);
    }
}
