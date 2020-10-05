package com.carRental.service;

import com.carRental.domain.User;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestSuite {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void saveUserTest() {
        //Given
        User user = initUser();
        when(userRepository.save(user)).thenReturn(user);

        //When
        User result = userService.saveUser(user);

        //Then
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    public void getUserByIdTest() throws UserNotFoundException {
        //Given
        User user = initUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //When
        User result = userService.getUserById(1L);

        //Then
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void getUserByEmailTest() throws UserNotFoundException {
        //Given
        User user = initUser();
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));

        //When
        User result = userService.getUserByEmail("email");

        //Then
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    public void getUserByPhoneNumberTest() throws UserNotFoundException {
        //Given
        User user = initUser();
        when(userRepository.findByPhoneNumber(123456)).thenReturn(Optional.of(user));

        //When
        User result = userService.getUserByPhoneNumber(123456);

        //Then
        assertEquals(user.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    public void getAllUsersTest() {
        //Given
        User user = initUser();
        List<User> userList = Collections.singletonList(user);
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

    @Test
    public void isUserAlreadyRegisteredTest() {
        //Given
        when(userRepository.existsByEmail("email")).thenReturn(true);

        //When
        boolean result = userRepository.existsByEmail("email");

        assertTrue(result);
    }

    private User initUser() {
        return new User(
                1L,
                "Jack",
                "Smith",
                "email",
                "password",
                123456);
    }
}
