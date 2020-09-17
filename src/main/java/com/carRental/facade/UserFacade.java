package com.carRental.facade;

import com.carRental.domain.dto.UserDto;
import com.carRental.domain.dto.emailVerificationApi.EmailVerificationDto;
import com.carRental.exceptions.InvalidEmailException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.mapper.UserMapper;
import com.carRental.service.EmailVerificationService;
import com.carRental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EmailVerificationService emailVerificationService;

    @Autowired
    public UserFacade(UserService userService, UserMapper userMapper, EmailVerificationService emailVerificationService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.emailVerificationService = emailVerificationService;
    }

    public UserDto saveUser(UserDto userDto) throws InvalidEmailException {
        if (isEmailValid(userDto.getEmail())) {
            return userMapper.mapToUserDto(userService.saveUser(userMapper.mapToUser(userDto)));
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

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    private boolean isEmailValid(final String email) {
        EmailVerificationDto emailVerificationDto = emailVerificationService.verifyEmail(email);
        return emailVerificationDto.getDnsCheck().equals("true") && emailVerificationDto.getFormatCheck().equals("true")
                && emailVerificationDto.getSmtpCheck().equals("true");
    }
}
