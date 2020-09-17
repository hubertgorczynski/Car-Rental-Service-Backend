package com.carRental.controller;

import com.carRental.domain.dto.emailVerificationApi.EmailVerificationDto;
import com.carRental.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/email_verification")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @Autowired
    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping("/{email}")
    public EmailVerificationDto verifyEmail(@PathVariable String email) {
        return emailVerificationService.isEmailValid(email);
    }
}
