package com.carRental.extrernalApi.hereApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class HereApiConfig {
    @Value("${here.api.endpoint}")
    private String hereApiEndpoint;

    @Value("${here.api.key}")
    private String hereApiKey;
}
