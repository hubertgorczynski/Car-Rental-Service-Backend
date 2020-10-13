package com.carRental.service;

import com.carRental.domain.dto.hereApi.*;
import com.carRental.extrernalApi.hereApi.client.HereApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HereApiServiceTestSuite {

    @InjectMocks
    private HereApiService hereApiService;

    @Mock
    private HereApiClient hereApiClient;

    @Test
    public void locateGeocodeTest() {
        //Given
        GeocodePositionDto geocodePositionDto = new GeocodePositionDto("-52N", "35E");
        GeocodeResultDto geocodeResultDto = new GeocodeResultDto("title", geocodePositionDto);
        List<GeocodeResultDto> geocodeResultDtoList = Collections.singletonList(geocodeResultDto);
        GeocodeDto geocodeDto = new GeocodeDto(geocodeResultDtoList);

        when(hereApiClient.locateGeocode("61-132")).thenReturn(geocodeDto);

        //When
        GeocodeDto result = hereApiService.locateGeocode("61-132");

        //Then
        assertEquals("title", result.getGeocodeResultDtoList().get(0).getTitle());
        assertEquals("-52N", result.getGeocodeResultDtoList().get(0).getPosition().getLatitude());
        assertEquals("35E", result.getGeocodeResultDtoList().get(0).getPosition().getLongitude());
    }

    @Test
    public void searchCarRentalAgenciesTestSuite() {
        //Given
        CarAgencyAddressDto carAgencyAddressDto = new CarAgencyAddressDto(
                "Mickiewicza",
                "19",
                "61-123",
                "Poznan",
                "Wielkopolska",
                "Polska");
        CarAgencyResultDto carAgencyResultDto = new CarAgencyResultDto("Wypozyczalnia19", carAgencyAddressDto);
        List<CarAgencyResultDto> carAgencyResultDtoList = Collections.singletonList(carAgencyResultDto);
        CarAgencyDto carAgencyDto = new CarAgencyDto(carAgencyResultDtoList);

        when(hereApiClient.searchCarRentalAgencies("25.41", "56.26")).thenReturn(carAgencyDto);

        //When
        CarAgencyDto result = hereApiService.searchCarRentalAgencies("25.41", "56.26");

        //Then
        assertEquals("Poznan", result.getAgenciesDtoList().get(0).getAddress().getCity());
        assertEquals("Mickiewicza", result.getAgenciesDtoList().get(0).getAddress().getStreet());
    }
}
