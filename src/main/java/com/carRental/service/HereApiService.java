package com.carRental.service;

import com.carRental.domain.dto.hereApi.CarAgencyDto;
import com.carRental.domain.dto.hereApi.GeocodeDto;
import com.carRental.hereApi.client.HereApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HereApiService {

    private final HereApiClient hereApiClient;

    @Autowired
    public HereApiService(HereApiClient hereApiClient) {
        this.hereApiClient = hereApiClient;
    }

    public GeocodeDto locateGeocode(String postalCode) {
        return hereApiClient.locateGeocode(postalCode);
    }

    public CarAgencyDto searchCarRentalAgencies(double latitude, double longitude) {
        return hereApiClient.searchCarRentalAgencies(latitude, longitude);
    }
}
