package com.carRental.vinDecoderApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class VinDecoderConfig {
    @Value("${vindecoder.api.endpoint}")
    private String vinDecoderEndpoint;
}
