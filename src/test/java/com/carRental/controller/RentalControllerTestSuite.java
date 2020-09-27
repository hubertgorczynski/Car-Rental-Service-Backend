package com.carRental.controller;

import com.carRental.domain.dto.RentalComplexDto;
import com.carRental.facade.RentalFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RentalController.class)
public class RentalControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalFacade rentalFacade;

    RentalComplexDto rentalComplexDto = new RentalComplexDto(
            1L,
            LocalDate.of(2020, 10, 10),
            LocalDate.of(2020, 10, 15),
            new BigDecimal(125),
            "Audi",
            "A3",
            "Jack",
            "Smith",
            "email",
            123456);

    @Test
    public void shouldFetchRentalById() throws Exception {
        //Given
        when(rentalFacade.getRentalById(1L)).thenReturn(rentalComplexDto);

        //When & Then
        mockMvc.perform(get("/v1/rentals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldFetchAllRentals() throws Exception {
        //Given
        List<RentalComplexDto> rentalComplexDtoList = Collections.singletonList(rentalComplexDto);
        when(rentalFacade.getRentals()).thenReturn(rentalComplexDtoList);

        //When & Then
        mockMvc.perform(get("/v1/rentals")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].carBrand", is("Audi")));
    }

    @Test
    public void shouldCloseRental() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/rentals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().is(200));
    }
}
