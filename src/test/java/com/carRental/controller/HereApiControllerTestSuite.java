package com.carRental.controller;

import com.carRental.domain.dto.hereApi.*;
import com.carRental.service.HereApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HereApiController.class)
public class HereApiControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HereApiService hereApiService;

    @Test
    public void shouldFetchCoordinates() throws Exception {
        //Given
        GeocodePositionDto geocodePositionDto = new GeocodePositionDto("-52N", "35E");
        GeocodeResultDto geocodeResultDto = new GeocodeResultDto("title", geocodePositionDto);
        List<GeocodeResultDto> geocodeResultDtoList = Collections.singletonList(geocodeResultDto);
        GeocodeDto geocodeDto = new GeocodeDto(geocodeResultDtoList);

        when(hereApiService.locateGeocode("61-233")).thenReturn(geocodeDto);

        //When & Then
        mockMvc.perform(get("/v1/hereApi/check_coordinates_by_postal_code/61-233")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("postalCode", "61-233"))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldFetchCarAgencies() throws Exception {
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

        when(hereApiService.searchCarRentalAgencies(52.12, 42.41)).thenReturn(carAgencyDto);

        //When & Then
        mockMvc.perform(get("/v1/hereApi/search_nearest_agencies_by_coordinates")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("latitude", String.valueOf(52.12))
                .param("longitude", String.valueOf(42.41)))
                .andExpect(status().is(200));
    }
}
