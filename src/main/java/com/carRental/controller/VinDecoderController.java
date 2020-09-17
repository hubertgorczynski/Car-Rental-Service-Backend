package com.carRental.controller;

import com.carRental.domain.dto.vinDecoderApi.VinDecodedDto;
import com.carRental.service.VinDecoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/vin_decoder")
public class VinDecoderController {

    private final VinDecoderService vinDecoderService;

    @Autowired
    public VinDecoderController(VinDecoderService vinDecoderService) {
        this.vinDecoderService = vinDecoderService;
    }

    @GetMapping("/{vin}")
    public VinDecodedDto decodeVinNumber(@PathVariable String vin) {
        return vinDecoderService.decode(vin);
    }
}
