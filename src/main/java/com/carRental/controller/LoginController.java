package com.carRental.controller;

import com.carRental.domain.dto.LoginDto;
import com.carRental.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/logins")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/is_already_registered")
    public boolean isLoginRegistered(@RequestBody LoginDto loginDto) {
        return loginService.isLoginRegistered(loginDto);
    }
}
