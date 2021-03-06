package com.carRental.service;

import com.carRental.domain.dto.vinDecoderApi.VinDecodedDto;
import com.carRental.extrernalApi.vinDecoderApi.client.VinDecoderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VinDecoderService {

    private final VinDecoderClient vinDecoderClient;

    @Autowired
    public VinDecoderService(VinDecoderClient vinDecoderClient) {
        this.vinDecoderClient = vinDecoderClient;
    }

    public VinDecodedDto decode(String vin) {
        return vinDecoderClient.decodeVin(vin);
    }
}
