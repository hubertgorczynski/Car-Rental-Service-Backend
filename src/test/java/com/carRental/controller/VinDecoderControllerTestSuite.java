package com.carRental.controller;

import com.carRental.domain.dto.vinDecoderApi.VinDecodedDto;
import com.carRental.domain.dto.vinDecoderApi.VinResultDto;
import com.carRental.service.VinDecoderService;
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
@WebMvcTest(VinDecoderController.class)
public class VinDecoderControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VinDecoderService vinDecoderService;

    @Test
    public void shouldDecodeVin() throws Exception {
        //Given
        VinResultDto vinResultDto = new VinResultDto(
                "Lexus",
                "IS",
                "2015",
                "Gasoline",
                "Saloon",
                "PassengerCar",
                "Japan",
                "Tokyo"
        );
        List<VinResultDto> vinResultDtoList = Collections.singletonList(vinResultDto);
        VinDecodedDto vinDecodedDto = new VinDecodedDto(vinResultDtoList);

        when(vinDecoderService.decode("JTHFF2C26B2515141")).thenReturn(vinDecodedDto);

        //When & Then
        mockMvc.perform(get("/v1/vin_decoder/JTHFF2C26B2515141")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("vin", "JTHFF2C26B2515141"))
                .andExpect(status().is(200));
    }
}
