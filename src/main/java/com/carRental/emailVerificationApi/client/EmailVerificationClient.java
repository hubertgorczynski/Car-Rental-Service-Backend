package com.carRental.emailVerificationApi.client;

import com.carRental.domain.dto.emailVerificationApi.EmailVerificationDto;
import com.carRental.emailVerificationApi.config.EmailVerificationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class EmailVerificationClient {

    private final RestTemplate restTemplate;
    private final EmailVerificationConfig emailVerificationConfig;

    @Autowired
    public EmailVerificationClient(RestTemplate restTemplate, EmailVerificationConfig emailVerificationConfig) {
        this.restTemplate = restTemplate;
        this.emailVerificationConfig = emailVerificationConfig;
    }

    public EmailVerificationDto verifyEmail(String email) {
        URI url = getEmailVerificationUri(email);
        return restTemplate.getForObject(url, EmailVerificationDto.class);
    }

    private URI getEmailVerificationUri(String email) {
        return UriComponentsBuilder.fromHttpUrl(emailVerificationConfig.getEmailVerificationApiEndpoint())
                .queryParam("apiKey", emailVerificationConfig.getEmailVerificationApiKey())
                .queryParam("emailAddress", email)
                .build().encode().toUri();
    }
}
