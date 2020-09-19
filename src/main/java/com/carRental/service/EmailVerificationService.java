package com.carRental.service;

import com.carRental.domain.dto.emailVerificationApi.EmailVerificationDto;
import com.carRental.extrernalApi.emailVerificationApi.client.EmailVerificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    private final EmailVerificationClient emailVerificationClient;

    @Autowired
    public EmailVerificationService(EmailVerificationClient emailVerificationClient) {
        this.emailVerificationClient = emailVerificationClient;
    }

    public EmailVerificationDto verifyEmail(final String email) {
        return emailVerificationClient.verifyEmail(email);
    }
}
