package com.carRental.controller;

import com.carRental.domain.dto.EmailVerificationDto;
import com.carRental.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/verification")
public class EmailVerificationController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping(value = "/{email}")
    public EmailVerificationDto verifyEmail(@PathVariable String email) {
        return emailVerificationService.isEmailValid(email);
    }
}

