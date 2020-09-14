package com.carRental.vinDecoderApi.client;

import com.carRental.domain.dto.VinDecodedDto;
import com.carRental.vinDecoderApi.config.VinDecoderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class VinDecoderClient {

    private final RestTemplate restTemplate;
    private final VinDecoderConfig vinDecoderConfig;

    @Autowired
    public VinDecoderClient(RestTemplate restTemplate, VinDecoderConfig vinDecoderConfig) {
        this.restTemplate = restTemplate;
        this.vinDecoderConfig = vinDecoderConfig;
    }

    public VinDecodedDto decodeVin(String vin) {
        URI url = getVinDecoderUri(vin);
        return restTemplate.getForObject(url, VinDecodedDto.class);
    }

    private URI getVinDecoderUri(String vin) {
        return UriComponentsBuilder.fromHttpUrl(vinDecoderConfig.getVinDecoderEndpoint())
                .queryParam("", vin)
                .queryParam("format", "json")
                .build().encode().toUri();
    }
}
