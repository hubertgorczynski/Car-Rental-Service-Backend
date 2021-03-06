package com.carRental.extrernalApi.vinDecoderApi.client;

import com.carRental.domain.dto.vinDecoderApi.VinDecodedDto;
import com.carRental.extrernalApi.vinDecoderApi.config.VinDecoderConfig;
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
        return UriComponentsBuilder.fromHttpUrl(vinDecoderConfig.getVinDecoderEndpoint() + "/" + vin)
                .queryParam("format", "json")
                .build().encode().toUri();
    }
}
