package com.carRental.controller;

import com.carRental.domain.dto.UserDto;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public UserDto getUserById(@RequestParam Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/usersByEmail")
    public UserDto getUserByEmail(@RequestParam String email) throws UserNotFoundException {
        return userService.getUserByEmail(email);
    }

    @PostMapping(value = "/user")
    public void createUser(@RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
    }
}
