package com.carRental.mapper;

import com.carRental.domain.Car;
import com.carRental.domain.Status;
import com.carRental.domain.dto.CarDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CarMapperTestSuite {

    @InjectMocks
    private CarMapper carMapper;

    Car car = new Car(
            1L,
            "sampleVin",
            "Audi",
            "A3",
            2015,
            "Diesel",
            3.0,
            "Saloon",
            250000,
            new BigDecimal(25));

    CarDto carDto = new CarDto(
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

    @Test
    public void mapToCarTest() {
        //Given

        //When
        Car mappedCar = carMapper.mapToCar(carDto);

        //Then
        assertEquals(car.getId(), mappedCar.getId());
        assertEquals(car.getBrand(), mappedCar.getBrand());
        assertEquals(car.getProductionYear(), mappedCar.getProductionYear());
        assertEquals(car.getStatus(), mappedCar.getStatus());
    }

    @Test
    public void mapToCarDtoTest() {
        //Given

        //When
        CarDto mappedCarDto = carMapper.mapToCarDto(car);

        //Then
        assertEquals(carDto.getId(), mappedCarDto.getId());
        assertEquals(carDto.getBrand(), mappedCarDto.getBrand());
        assertEquals(carDto.getProductionYear(), mappedCarDto.getProductionYear());
        assertEquals(car.getStatus(), mappedCarDto.getStatus());
    }

    @Test
    public void mapToCarDtoList() {
        //Given
        List<Car> carList = Arrays.asList(car);

        //When
        List<CarDto> mappedCarDtoList = carMapper.mapToCarDtoList(carList);

        //Then
        assertNotNull(mappedCarDtoList);
        assertEquals(1, mappedCarDtoList.size());

        mappedCarDtoList.forEach(c -> {
            assertEquals(c.getId(), carDto.getId());
            assertEquals(c.getBrand(), carDto.getBrand());
            assertEquals(c.getProductionYear(), carDto.getProductionYear());
            assertEquals(c.getStatus(), carDto.getStatus());
        });
    }
}
