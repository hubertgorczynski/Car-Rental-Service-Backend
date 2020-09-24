package com.carRental.service;

import com.carRental.domain.Car;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTestSuite {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    Car car1 = new Car(
            1L,
            "sampleVin",
            "Audi",
            "A3",
            2015,
            "Diesel",
            3.0,
            "Saloon",
            110000,
            new BigDecimal(18));

    Car car2 = new Car(
            2L,
            "sampleVin",
            "Mercedes",
            "AMG",
            2015,
            "Diesel",
            3.0,
            "Saloon",
            250000,
            new BigDecimal(25));

    Car car3 = new Car(
            3L,
            "sampleVin",
            "BMW",
            "Q3",
            2015,
            "Gasoline",
            3.0,
            "SUV",
            140000,
            new BigDecimal(15));

    @Test
    public void saveCarTest() {
        //Given
        when(carRepository.save(car1)).thenReturn(car1);

        //When
        Car result = carService.saveCar(car1);

        //Then
        assertEquals(car1.getId(), result.getId());
        assertEquals(car1.getBrand(), result.getBrand());
        assertEquals(car1.getProductionYear(), result.getProductionYear());
    }

    @Test
    public void getCarByIdTest() throws CarNotFoundException {
        //Given
        when(carRepository.findById(1L)).thenReturn(Optional.of(car1));

        //When
        Car result = carService.getCarById(1L);

        //Then
        assertEquals(car1.getId(), result.getId());
    }

    @Test
    public void getCarByVinTest() throws CarNotFoundException {
        //Given
        when(carRepository.findByVin("sampleVin")).thenReturn(Optional.of(car1));

        //When
        Car result = carService.getCarByVin("sampleVin");

        //Then
        assertEquals(car1.getVin(), result.getVin());
    }

    @Test
    public void getAllCarsTest() {
        //Given
        List<Car> carList = Arrays.asList(car1, car2, car3);
        when(carRepository.findAll()).thenReturn(carList);

        //When
        List<Car> resultList = carService.getCars();

        //Then
        assertNotNull(resultList);
        assertEquals(3, resultList.size());
    }

    @Test
    public void getCarsByBrandTest() {
        //Given
        List<Car> carByBrandList = Arrays.asList(car1);
        when(carRepository.findAllByBrand("Audi")).thenReturn(carByBrandList);

        //When
        List<Car> resultList = carService.getCarsByBrand("Audi");

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(car1.getBrand(), resultList.get(0).getBrand());
    }

    @Test
    public void getCarsByFuelTypeTest() {
        //Given
        List<Car> carByFuelTypeList = Arrays.asList(car1, car2);

        when(carRepository.findAllByFuelType("Diesel")).thenReturn(carByFuelTypeList);

        //When
        List<Car> resultList = carService.getCarsByFuelType("Diesel");

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(car1.getFuelType(), resultList.get(0).getFuelType());
        assertEquals(car2.getFuelType(), resultList.get(1).getFuelType());
    }

    @Test
    public void getCarsByBodyClassTypeTest() {
        //Given
        List<Car> carByBodyClassList = Arrays.asList(car1, car2);

        when(carRepository.findAllByBodyClass("Saloon")).thenReturn(carByBodyClassList);

        //When
        List<Car> resultList = carService.getCarsByBodyClass("Saloon");

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(car1.getBodyClass(), resultList.get(0).getBodyClass());
        assertEquals(car2.getBodyClass(), resultList.get(1).getBodyClass());
    }

    @Test
    public void getCarsByCostPerDayLessThenTest() {
        //Given
        List<Car> carByCostList = Arrays.asList(car1, car3);

        when(carRepository.findAllByCostPerDayLessThan(new BigDecimal(20))).thenReturn(carByCostList);

        //When
        List<Car> resultList = carService.getCarsByCostPerDayLessThan(new BigDecimal(20));

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(car1.getBodyClass(), resultList.get(0).getBodyClass());
        assertEquals(car3.getBodyClass(), resultList.get(1).getBodyClass());
    }

    @Test
    public void getCarsByMileageLessThenTest() {
        //Given
        List<Car> carByMileageList = Arrays.asList(car1, car3);

        when(carRepository.findAllByMileageLessThan(200000)).thenReturn(carByMileageList);

        //When
        List<Car> resultList = carService.getCarsByMileageLessThen(200000);

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(car1.getBodyClass(), resultList.get(0).getBodyClass());
        assertEquals(car3.getBodyClass(), resultList.get(1).getBodyClass());
    }

    @Test
    public void deleteCarTest() {
        //Given

        //When
        carService.deleteCar(2L);

        //Then
        verify(carRepository, times(1)).deleteById(2L);
    }
}
