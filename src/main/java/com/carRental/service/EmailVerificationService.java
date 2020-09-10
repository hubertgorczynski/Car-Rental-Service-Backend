package com.carRental.service;

import com.carRental.emailVerificationApi.client.EmailVerificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailVerificationClient emailVerificationClient;

    public void isEmailValid(String email) {
        //return emailVerificationClient.verifyEmail(email);
    }
}
