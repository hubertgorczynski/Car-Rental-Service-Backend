package com.carRental.mapper;

import com.carRental.domain.Car;
import com.carRental.domain.Status;
import com.carRental.domain.dto.CarDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarMapperTestSuite {

    @Autowired
    private CarMapper carMapper;

    @Test
    public void mapToCarTest() {
        //Given
        CarDto carDto = initCarDto();

        //When
        Car mappedCar = carMapper.mapToCar(carDto);

        //Then
        assertEquals(1L, (long) mappedCar.getId());
        assertEquals("sampleVin", mappedCar.getVin());
        assertEquals("Audi", mappedCar.getBrand());
        assertEquals("A3", mappedCar.getModel());
        assertEquals(2015, mappedCar.getProductionYear());
        assertEquals("Diesel", mappedCar.getFuelType());
        assertEquals(3.0, mappedCar.getEngineCapacity(), 0.001);
        assertEquals("Saloon", mappedCar.getBodyClass());
        assertEquals(250000, mappedCar.getMileage());
        assertEquals(Status.AVAILABLE, mappedCar.getStatus());
    }

    @Test
    public void mapToCarDtoTest() {
        //Given
        Car car = initCar();

        //When
        CarDto mappedCarDto = carMapper.mapToCarDto(car);

        //Then
        assertEquals(1L, (long) mappedCarDto.getId());
        assertEquals("sampleVin", mappedCarDto.getVin());
        assertEquals("Audi", mappedCarDto.getBrand());
        assertEquals("A3", mappedCarDto.getModel());
        assertEquals(2015, mappedCarDto.getProductionYear());
        assertEquals("Diesel", mappedCarDto.getFuelType());
        assertEquals(3.0, mappedCarDto.getEngineCapacity(), 0.001);
        assertEquals("Saloon", mappedCarDto.getBodyClass());
        assertEquals(250000, mappedCarDto.getMileage());
        assertEquals(Status.AVAILABLE, mappedCarDto.getStatus());
    }

    @Test
    public void mapToCarDtoList() {
        //Given
        Car car = initCar();
        List<Car> carList = Collections.singletonList(car);

        //When
        List<CarDto> mappedCarDtoList = carMapper.mapToCarDtoList(carList);

        //Then
        assertNotNull(mappedCarDtoList);
        assertEquals(1, mappedCarDtoList.size());
        assertEquals(1L, (long) mappedCarDtoList.get(0).getId());
        assertEquals("sampleVin", mappedCarDtoList.get(0).getVin());
        assertEquals("Audi", mappedCarDtoList.get(0).getBrand());
        assertEquals("A3", mappedCarDtoList.get(0).getModel());
        assertEquals(2015, mappedCarDtoList.get(0).getProductionYear());
        assertEquals("Diesel", mappedCarDtoList.get(0).getFuelType());
        assertEquals(3.0, mappedCarDtoList.get(0).getEngineCapacity(), 0.001);
        assertEquals("Saloon", mappedCarDtoList.get(0).getBodyClass());
        assertEquals(250000, mappedCarDtoList.get(0).getMileage());
        assertEquals(Status.AVAILABLE, mappedCarDtoList.get(0).getStatus());
    }

    private Car initCar() {
        return new Car(
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
}
