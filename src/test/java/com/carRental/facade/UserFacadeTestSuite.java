package com.carRental.facade;

import com.carRental.domain.Login;
import com.carRental.domain.User;
import com.carRental.domain.dto.UserDto;
import com.carRental.domain.dto.emailVerificationApi.EmailVerificationDto;
import com.carRental.exceptions.InvalidEmailException;
import com.carRental.exceptions.LoginNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.mapper.UserMapper;
import com.carRental.service.LoginService;
import com.carRental.service.UserService;
import com.carRental.service.emailService.EmailToUsersService;
import com.carRental.service.emailService.EmailVerificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserFacadeTestSuite {

    @InjectMocks
    private UserFacade userFacade;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;

    @Mock
    private LoginService loginService;

    @Mock
    private EmailToUsersService emailToUsersService;

    @Mock
    private EmailVerificationService emailVerificationService;

    @Test
    public void userSaveTest() throws InvalidEmailException {
        //Given
        User user = initUser();
        UserDto userDto = initUserDto();
        EmailVerificationDto emailVerificationDto = initEmailVerificationDto();

        when(emailVerificationService.verifyEmail(any())).thenReturn(emailVerificationDto);
        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        doNothing().when(emailToUsersService).sendEmailAboutCreatingUser(user);
        doNothing().when(loginService).saveLogin(ArgumentMatchers.any());

        //When
        UserDto savedUser = userFacade.saveUser(userDto);

        //Then
        assertEquals(userDto.getId(), savedUser.getId());
        assertEquals(userDto.getName(), savedUser.getName());
        assertEquals(LocalDate.now(), savedUser.getAccountCreated());
        verify(emailToUsersService, times(1)).sendEmailAboutCreatingUser(user);
    }

    @Test
    public void modifyUserTest() throws InvalidEmailException, LoginNotFoundException, UserNotFoundException {
        //Given
        User user = initUser();
        UserDto userDto = initUserDto();
        Login login = initLogin();
        EmailVerificationDto emailVerificationDto = initEmailVerificationDto();

        when(emailVerificationService.verifyEmail(any())).thenReturn(emailVerificationDto);
        when(userMapper.mapToUser(any())).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);
        when(userMapper.mapToUserDto(any())).thenReturn(userDto);
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(loginService.getLoginByEmailAndPassword(anyString(), anyString())).thenReturn(login);

        //When
        UserDto modifiedUser = userFacade.modifyUser(userDto);

        //Then
        assertEquals(userDto.getName(), modifiedUser.getName());
        assertEquals(userDto.getEmail(), modifiedUser.getEmail());
        assertEquals(userDto.getPhoneNumber(), modifiedUser.getPhoneNumber());
    }

    @Test
    public void getUserByIdTest() throws UserNotFoundException {
        //Given
        User user = initUser();
        UserDto userDto = initUserDto();

        when(userService.getUserById(1L)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        UserDto result = userFacade.getUserById(1L);

        //Then
        assertEquals(userDto.getId(), result.getId());
    }

    @Test
    public void getUserByEmailTest() throws UserNotFoundException {
        //Given
        User user = initUser();
        UserDto userDto = initUserDto();

        when(userService.getUserByEmail("email")).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        UserDto result = userFacade.getUserByEmail("email");

        //Then
        assertEquals(userDto.getEmail(), result.getEmail());
    }

    @Test
    public void getUserByPhoneNumberTest() throws UserNotFoundException {
        //Given
        User user = initUser();
        UserDto userDto = initUserDto();

        when(userService.getUserByPhoneNumber(123456)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        UserDto result = userFacade.getUserByPhoneNumber(123456);

        //Then
        assertEquals(userDto.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    public void getUsersTest() {
        //Given
        User user = initUser();
        UserDto userDto = initUserDto();

        List<User> userList = Collections.singletonList(user);
        List<UserDto> userDtoList = Collections.singletonList(userDto);

        when(userService.getAllUsers()).thenReturn(userList);
        when(userMapper.mapToUserDtoList(userList)).thenReturn(userDtoList);

        //When
        List<UserDto> resultList = userFacade.getAllUsers();

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        resultList.forEach(u -> {
            assertEquals(u.getId(), userDto.getId());
            assertEquals(u.getName(), userDto.getName());
            assertEquals(u.getPhoneNumber(), userDto.getPhoneNumber());
            assertEquals(u.getPassword(), userDto.getPassword());
        });
    }

    @Test
    public void deleteUserTest() throws LoginNotFoundException, UserNotFoundException {
        //Given
        User user = initUser();
        Login login = initLogin();

        when(userService.getUserById(anyLong())).thenReturn(user);
        when(loginService.getLoginByEmailAndPassword(anyString(), anyString())).thenReturn(login);
        doNothing().when(loginService).deleteLogin(any());

        //When
        userFacade.deleteUser(2L);

        //Then
        verify(userService, times(1)).deleteUser(2L);
    }

    @Test
    public void isUserAlreadyRegisteredTest() {
        //Given
        when(userService.isUserAlreadyRegistered("email")).thenReturn(true);

        //When
        boolean result = userFacade.isUserAlreadyRegistered("email");

        assertTrue(result);
    }

    @Test
    public void isEmailValidTest() {
        //Given
        EmailVerificationDto emailVerificationDto = new EmailVerificationDto("true", "true", "true");
        when(emailVerificationService.verifyEmail("email")).thenReturn(emailVerificationDto);

        //When
        boolean result = userFacade.isEmailValid("email");

        //Then
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

    private UserDto initUserDto() {
        return new UserDto(
                1L,
                "Jack",
                "Smith",
                "email",
                "password",
                123456,
                LocalDate.now());
    }

    private EmailVerificationDto initEmailVerificationDto() {
        return new EmailVerificationDto("true", "true", "true");
    }

    private Login initLogin() {
        return new Login(
                "email",
                "password"
        );
    }
}
