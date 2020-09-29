package com.carRental.externalApi.vinDecoderApi;

import com.carRental.domain.dto.vinDecoderApi.VinDecodedDto;
import com.carRental.domain.dto.vinDecoderApi.VinResultDto;
import com.carRental.extrernalApi.vinDecoderApi.client.VinDecoderClient;
import com.carRental.extrernalApi.vinDecoderApi.config.VinDecoderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VinDecoderClientTestSuite {

    @InjectMocks
    private VinDecoderClient vinDecoderClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private VinDecoderConfig vinDecoderConfig;

    @Test
    public void shouldDecodeVin() throws URISyntaxException {
        //Given
        when(vinDecoderConfig.getVinDecoderEndpoint()).thenReturn("https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues");

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

        URI uri = new URI("https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/WAUFEAFMXCA868546?format=json");
        when(restTemplate.getForObject(uri, VinDecodedDto.class)).thenReturn(vinDecodedDto);

        //When
        VinDecodedDto result = vinDecoderClient.decodeVin("WAUFEAFMXCA868546");

        //Then
        assertEquals("Audi", result.getResultsDtoList().get(0).getManufacturer());
        assertEquals("A3", result.getResultsDtoList().get(0).getModel());
    }
}
