package com.carRental.hereApi.client;

import com.carRental.domain.dto.hereApi.CarAgencyDto;
import com.carRental.domain.dto.hereApi.GeocodeDto;
import com.carRental.hereApi.config.HereApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class HereApiClient {

    private final RestTemplate restTemplate;
    private final HereApiConfig hereApiConfig;

    @Autowired
    public HereApiClient(RestTemplate restTemplate, HereApiConfig hereApiConfig) {
        this.restTemplate = restTemplate;
        this.hereApiConfig = hereApiConfig;
    }

    public GeocodeDto locateGeocode(String postalCode) {
        URI url = getLocalizerUri(postalCode);
        return restTemplate.getForObject(url, GeocodeDto.class);
    }

    public CarAgencyDto searchCarRentalAgencies(String geoCoordinates) {
        URI url = getAgenciesSearcherUri(geoCoordinates);
        return restTemplate.getForObject(url, CarAgencyDto.class);
    }

    private URI getLocalizerUri(String postalCode) {
        return UriComponentsBuilder.fromHttpUrl(hereApiConfig.getHereApiEndpoint() + "/geocode")
                .queryParam("apiKey", hereApiConfig.getHereApiKey())
                .queryParam("qq", "postalCode=" + postalCode)
                .queryParam("limit", 1)
                .build().encode().toUri();
    }

    private URI getAgenciesSearcherUri(String geoCoordinates) {
        return UriComponentsBuilder.fromHttpUrl(hereApiConfig.getHereApiEndpoint() + "/browse")
                .queryParam("apiKey", hereApiConfig.getHereApiKey())
                .queryParam("at", geoCoordinates)
                .queryParam("categories", "700-7851-0117")
                .queryParam("limit", 5)
                .build().encode().toUri();
    }
}
