package com.carRental.controller;

import com.carRental.domain.dto.UserDto;
import com.carRental.exceptions.InvalidEmailException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userFacade.getUserById(id);
    }

    @GetMapping("/by_email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return userFacade.getUserByEmail(email);
    }

    @GetMapping("/by_phone/{number}")
    public UserDto getUserByPhoneNumber(@PathVariable int number) throws UserNotFoundException {
        return userFacade.getUserByPhoneNumber(number);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userFacade.getAllUsers();
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) throws InvalidEmailException {
        return userFacade.saveUser(userDto);
    }

    @PutMapping
    public UserDto modifyUser(@RequestBody UserDto userDto) throws InvalidEmailException {
        return userFacade.saveUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
    }
}
