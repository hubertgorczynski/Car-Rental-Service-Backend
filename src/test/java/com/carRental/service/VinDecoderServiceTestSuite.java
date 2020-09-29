package com.carRental.service;

import com.carRental.domain.dto.vinDecoderApi.VinDecodedDto;
import com.carRental.domain.dto.vinDecoderApi.VinResultDto;
import com.carRental.extrernalApi.vinDecoderApi.client.VinDecoderClient;
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
public class VinDecoderServiceTestSuite {

    @InjectMocks
    private VinDecoderService vinDecoderService;

    @Mock
    private VinDecoderClient vinDecoderClient;

    @Test
    public void decodeTest() {
        //Given
        VinResultDto vinResultDto = new VinResultDto(
                "Audi",
                "A3",
                "2012",
                "Gasoline",
                "Wagon",
                "Passenger",
                "Germany",
                "Berlin");
        List<VinResultDto> vinResultDtoList = Collections.singletonList(vinResultDto);
        VinDecodedDto vinDecodedDto = new VinDecodedDto(vinResultDtoList);

        when(vinDecoderClient.decodeVin("WAUFEAFMXCA868546")).thenReturn(vinDecodedDto);

        //When
        VinDecodedDto result = vinDecoderService.decode("WAUFEAFMXCA868546");

        //Then
        assertEquals("A3", result.getResultsDtoList().get(0).getModel());
        assertEquals("Germany", result.getResultsDtoList().get(0).getPlantCountry());
    }
}
