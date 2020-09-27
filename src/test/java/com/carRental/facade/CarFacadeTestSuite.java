package com.carRental.facade;

import com.carRental.domain.Car;
import com.carRental.domain.Status;
import com.carRental.domain.dto.CarDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.mapper.CarMapper;
import com.carRental.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarFacadeTestSuite {

    @InjectMocks
    private CarFacade carFacade;

    @Mock
    private CarService carService;

    @Mock
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

    List<Car> carList = Collections.singletonList(car);
    List<CarDto> carDtoList = Collections.singletonList(carDto);

    @Test
    public void saveCarTest() {
        //Given
        when(carMapper.mapToCar(any())).thenReturn(car);
        when(carMapper.mapToCarDto(any())).thenReturn(carDto);

        //When
        CarDto savedCar = carFacade.saveCar(carDto);

        //Then
        assertEquals(carDto.getId(), savedCar.getId());
        assertEquals(carDto.getBrand(), savedCar.getBrand());
        assertEquals(carDto.getCostPerDay(), savedCar.getCostPerDay());
    }

    @Test
    public void getCarByIdTest() throws CarNotFoundException {
        //Given
        when(carService.getCarById(1L)).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        //When
        CarDto result = carFacade.getCarById(1L);

        //Then
        assertEquals(carDto.getId(), result.getId());
        assertEquals(carDto.getBrand(), result.getBrand());
    }

    @Test
    public void getCarByVinTest() throws CarNotFoundException {
        //Given
        when(carService.getCarByVin("sampleVin")).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        //When
        CarDto result = carFacade.getCarByVin("sampleVin");

        //Then
        assertEquals(carDto.getId(), result.getId());
        assertEquals(carDto.getVin(), result.getVin());
    }

    @Test
    public void getCarsTest() {
        //Given
        when(carService.getCars()).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When
        List<CarDto> resultList = carFacade.getCars();

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        resultList.forEach(c -> {
            assertEquals(c.getId(), carDto.getId());
            assertEquals(c.getStatus(), carDto.getStatus());
        });
    }

    @Test
    public void getCarsByBrandTest() {
        //Given
        when(carService.getCarsByBrand("Audi")).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When
        List<CarDto> resultList = carFacade.getCarsByBrand("Audi");

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        resultList.forEach(c -> {
            assertEquals(c.getId(), carDto.getId());
            assertEquals(c.getBrand(), carDto.getBrand());
        });
    }

    @Test
    public void getCarsByFuelTypeTest() {
        //Given
        when(carService.getCarsByFuelType("Diesel")).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When
        List<CarDto> resultList = carFacade.getCarsByFuelType("Diesel");

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        resultList.forEach(c -> {
            assertEquals(c.getId(), carDto.getId());
            assertEquals(c.getFuelType(), carDto.getFuelType());
        });
    }

    @Test
    public void getCarsByBodyClassTest() {
        //Given
        when(carService.getCarsByBodyClass("Saloon")).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When
        List<CarDto> resultList = carFacade.getCarsByBodyClass("Saloon");

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        resultList.forEach(c -> {
            assertEquals(c.getId(), carDto.getId());
            assertEquals(c.getBodyClass(), carDto.getBodyClass());
        });
    }

    @Test
    public void getCarsByMileageLessThenTest() {
        //Given
        when(carService.getCarsByMileageLessThen(260000)).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When
        List<CarDto> resultList = carFacade.getCarsByMileageLessThen(260000);

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        resultList.forEach(c -> {
            assertEquals(c.getId(), carDto.getId());
            assertEquals(c.getMileage(), carDto.getMileage());
        });
    }

    @Test
    public void getCarsByCostPerDayLessThenTest() {
        //Given
        when(carService.getCarsByCostPerDayLessThan(new BigDecimal(32))).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When
        List<CarDto> resultList = carFacade.getCarsByCostPerDayLessThan(new BigDecimal(32));

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        resultList.forEach(c -> {
            assertEquals(c.getId(), carDto.getId());
            assertEquals(c.getCostPerDay(), carDto.getCostPerDay());
        });
    }

    @Test
    public void deleteCarTest() {
        //Given
        //When
        carFacade.deleteCar(2L);

        //Then
        verify(carService, times(1)).deleteCar(2L);
    }
}
