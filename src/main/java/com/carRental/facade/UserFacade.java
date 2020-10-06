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
import com.carRental.service.emailService.EmailToUsersService;
import com.carRental.service.emailService.EmailVerificationService;
import com.carRental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EmailVerificationService emailVerificationService;
    private final EmailToUsersService emailToUsersService;
    private final LoginService loginService;

    @Autowired
    public UserFacade(UserService userService, UserMapper userMapper, EmailVerificationService emailVerificationService,
                      EmailToUsersService emailToUsersService, LoginService loginService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.emailVerificationService = emailVerificationService;
        this.emailToUsersService = emailToUsersService;
        this.loginService = loginService;
    }

    public UserDto saveUser(UserDto userDto) throws InvalidEmailException {
        if (isEmailValid(userDto.getEmail())) {
            saveLogin(userDto);
            User user = userMapper.mapToUser(userDto);
            user.setAccountCreated(LocalDate.now());
            User savedUser = userService.saveUser(user);
            emailToUsersService.sendEmailAboutCreatingUser(savedUser);
            return userMapper.mapToUserDto(savedUser);
        } else {
            throw new InvalidEmailException();
        }
    }

    public UserDto modifyUser(UserDto userDto) throws InvalidEmailException, LoginNotFoundException, UserNotFoundException {
        if (isEmailValid(userDto.getEmail())) {
            updateLogin(userDto);
            User user = userMapper.mapToUser(userDto);
            user.setAccountCreated(userDto.getAccountCreated());
            return userMapper.mapToUserDto(userService.saveUser(user));
        } else {
            throw new InvalidEmailException();
        }
    }

    public UserDto getUserById(Long id) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUserById(id));
    }

    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUserByEmail(email));
    }

    public UserDto getUserByPhoneNumber(int phoneNumber) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUserByPhoneNumber(phoneNumber));
    }

    public List<UserDto> getAllUsers() {
        return userMapper.mapToUserDtoList(userService.getAllUsers());
    }

    public void deleteUser(Long id) throws LoginNotFoundException, UserNotFoundException {
        deleteLogin(id);
        userService.deleteUser(id);
    }

    public boolean isEmailValid(String email) {
        EmailVerificationDto emailVerificationDto = emailVerificationService.verifyEmail(email);
        return emailVerificationDto.getDnsCheck().equals("true") && emailVerificationDto.getFormatCheck().equals("true")
                && emailVerificationDto.getSmtpCheck().equals("true");
    }

    public boolean isUserAlreadyRegistered(String email) {
        return userService.isUserAlreadyRegistered(email);
    }

    public void saveLogin(UserDto userDto) {
        loginService.saveLogin(new Login(
                userDto.getEmail(),
                userDto.getPassword()));
    }

    public void updateLogin(UserDto userDto) throws LoginNotFoundException, UserNotFoundException {
        User oldUser = userService.getUserById(userDto.getId());
        Login login = loginService.getLoginByEmailAndPassword(oldUser.getEmail(), oldUser.getPassword());

        login.setEmail(userDto.getEmail());
        login.setPassword(userDto.getPassword());
    }

    public void deleteLogin(Long userId) throws UserNotFoundException, LoginNotFoundException {
        User user = userService.getUserById(userId);
        Login login = loginService.getLoginByEmailAndPassword(user.getEmail(), user.getPassword());
        loginService.deleteLogin(login);
    }
}
