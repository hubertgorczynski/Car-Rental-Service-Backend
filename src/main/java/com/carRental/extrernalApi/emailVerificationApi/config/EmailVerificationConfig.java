package com.carRental.extrernalApi.emailVerificationApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmailVerificationConfig {
    @Value("${emailverification.api.endpoint}")
    private String emailVerificationApiEndpoint;

    @Value("${emailverification.api.key}")
    private String emailVerificationApiKey;
}
