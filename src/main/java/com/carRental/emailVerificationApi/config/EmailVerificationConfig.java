package com.carRental.emailVerificationApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmailVerificationConfig {
    @Value("${rapid.api.host}")
    private String rapidApiHost;

    @Value("${rapid.api.key}")
    private String rapidApiKey;

    @Value("${emailverification.api.endpoint}")
    private String emailVerificationApiEndpoint;

    @Value("${emailverification.api.key}")
    private String emailVerificationApiKey;
}
