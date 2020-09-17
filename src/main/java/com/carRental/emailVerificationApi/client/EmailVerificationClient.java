package com.carRental.emailVerificationApi.client;

import com.carRental.domain.dto.emailVerificationApi.EmailVerificationDto;
import com.carRental.emailVerificationApi.config.EmailVerificationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
        HttpEntity<?> request = new HttpEntity<>(prepareHeaders());

        HttpEntity<EmailVerificationDto> response = restTemplate.exchange(
                url.toString(),
                HttpMethod.GET,
                request,
                EmailVerificationDto.class);
        return response.getBody();
    }

    private HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Host", emailVerificationConfig.getRapidApiHost());
        headers.set("X-RapidAPI-Key", emailVerificationConfig.getRapidApiKey());
        return headers;
    }

    private URI getEmailVerificationUri(String email) {
        return UriComponentsBuilder.fromHttpUrl(emailVerificationConfig.getEmailVerificationApiEndpoint())
                .queryParam("key", emailVerificationConfig.getEmailVerificationApiKey())
                .queryParam("email", email)
                .build().encode().toUri();
    }
}
