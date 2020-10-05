package com.carRental.controller;

import com.carRental.domain.Status;
import com.carRental.domain.dto.CarDto;
import com.carRental.facade.CarFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarFacade carFacade;


    @Test
    public void shouldFetchCarById() throws Exception {
        //Given
        CarDto carDto = initCarDto();
        when(carFacade.getCarById(1L)).thenReturn(carDto);

        //When & Then
        mockMvc.perform(get("/v1/cars/by_id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.brand", is("Audi")));
    }

    @Test
    public void shouldFetchCarByVin() throws Exception {
        //Given
        CarDto carDto = initCarDto();
        when(carFacade.getCarByVin("sampleVin")).thenReturn(carDto);

        //When & Then
        mockMvc.perform(get("/v1/cars/by_vin/sampleVin")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("vin", "sampleVin"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.vin", is("sampleVin")));
    }

    @Test
    public void shouldFetchAllCars() throws Exception {
        //Given
        List<CarDto> carDtoList = initCarDtoList();
        when(carFacade.getCars()).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].brand", is("Audi")))
                .andExpect(jsonPath("$[0].mileage", is(250000)));
    }

    @Test
    public void shouldFetchAllCarsByBrand() throws Exception {
        //Given
        List<CarDto> carDtoList = initCarDtoList();
        when(carFacade.getCarsByBrand("Audi")).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .param("brand", "Audi"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].brand", is("Audi")));
    }

    @Test
    public void shouldFetchAllCarsByFuelType() throws Exception {
        //Given
        List<CarDto> carDtoList = initCarDtoList();
        when(carFacade.getCarsByFuelType("Diesel")).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .param("fuelType", "Diesel"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].fuelType", is("Diesel")));
    }

    @Test
    public void shouldFetchCarsByBodyClass() throws Exception {
        //Given
        List<CarDto> carDtoList = initCarDtoList();
        when(carFacade.getCarsByBodyClass("Saloon")).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bodyClass", "Saloon"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].bodyClass", is("Saloon")));
    }

    @Test
    public void shouldFetchCarsByMileage() throws Exception {
        //Given
        List<CarDto> carDtoList = initCarDtoList();
        when(carFacade.getCarsByMileageLessThen(300000)).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/v1/cars/by_mileage_less_then/300000")
                .contentType(MediaType.APPLICATION_JSON)
                .param("distance", String.valueOf(300000)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].vin", is("sampleVin")))
                .andExpect(jsonPath("$[0].mileage", is(250000)));
    }

    @Test
    public void shouldFetchCarsByCostPerDay() throws Exception {
        //Given
        List<CarDto> carDtoList = initCarDtoList();
        when(carFacade.getCarsByCostPerDayLessThan(new BigDecimal(30))).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/v1/cars/by_cost_per_day_less_then/30")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cost", String.valueOf(30)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].costPerDay", is(25)));

    }

    @Test
    public void shouldCreateCar() throws Exception {
        //Given
        CarDto carDto = initCarDto();
        when(carFacade.saveCar(ArgumentMatchers.any(CarDto.class))).thenReturn(carDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);

        //When & Then
        mockMvc.perform(post("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.vin", is("sampleVin")))
                .andExpect(jsonPath("$.brand", is("Audi")));
    }

    @Test
    public void shouldModifyCar() throws Exception {
        //Given
        CarDto carDto = initCarDto();
        when(carFacade.saveCar(ArgumentMatchers.any(CarDto.class))).thenReturn(carDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);

        //When & Then
        mockMvc.perform(put("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.bodyClass", is("Saloon")))
                .andExpect(jsonPath("$.fuelType", is("Diesel")));
    }

    @Test
    public void shouldDeleteCar() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().is(200));
    }

    private CarDto initCarDto() {
        return new CarDto(
                1L,
                "sampleVin",
                "Audi",
                "A3",
                2015,
                "Diesel",
                3.0,
                "Saloon",
                250000,
                new BigDecimal(25),
                Status.AVAILABLE);
    }

    private List<CarDto> initCarDtoList() {
        CarDto carDto = initCarDto();
        return Collections.singletonList(carDto);
    }
}
