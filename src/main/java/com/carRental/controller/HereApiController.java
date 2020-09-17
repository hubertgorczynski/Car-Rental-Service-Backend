package com.carRental.controller;

import com.carRental.domain.dto.hereApi.CarAgencyDto;
import com.carRental.domain.dto.hereApi.GeocodeDto;
import com.carRental.service.HereApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/hereApi")
public class HereApiController {

    private final HereApiService hereApiService;

    @Autowired
    public HereApiController(HereApiService hereApiService) {
        this.hereApiService = hereApiService;
    }

    @GetMapping("search_nearest_agencies_by_coordinates")
    public CarAgencyDto searchCarRentalAgencies(@RequestParam double latitude, @RequestParam double longitude) {
        return hereApiService.searchCarRentalAgencies(latitude, longitude);
    }

    @GetMapping("check_coordinates_by_postal_code/{postalCode}")
    public GeocodeDto get(@PathVariable String postalCode) {
        return hereApiService.locateGeocode(postalCode);
    }
}
