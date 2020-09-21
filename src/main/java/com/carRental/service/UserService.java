package com.carRental.service;

import com.carRental.domain.User;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.repository.UserRepository;
import com.carRental.service.emailService.EmailToUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailToUsersService emailToUsersService;

    @Autowired
    public UserService(UserRepository userRepository, EmailToUsersService emailToUsersService) {
        this.userRepository = userRepository;
        this.emailToUsersService = emailToUsersService;
    }

    public User saveUser(User user) {
        emailToUsersService.sendEmailAboutCreatingUser(user);
        return userRepository.save(user);
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByPhoneNumber(int phoneNumber) throws UserNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
