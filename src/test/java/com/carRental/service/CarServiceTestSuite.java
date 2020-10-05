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

    @Test
    public void saveCarTest() {
        //Given
        Car car = initCar();
        when(carRepository.save(car)).thenReturn(car);

        //When
        Car result = carService.saveCar(car);

        //Then
        assertEquals(car.getId(), result.getId());
        assertEquals(car.getBrand(), result.getBrand());
        assertEquals(car.getProductionYear(), result.getProductionYear());
    }

    @Test
    public void getCarByIdTest() throws CarNotFoundException {
        //Given
        Car car = initCar();
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        //When
        Car result = carService.getCarById(1L);

        //Then
        assertEquals(car.getId(), result.getId());
    }

    @Test
    public void getCarByVinTest() throws CarNotFoundException {
        //Given
        Car car = initCar();
        when(carRepository.findByVin("sampleVin")).thenReturn(Optional.of(car));

        //When
        Car result = carService.getCarByVin("sampleVin");

        //Then
        assertEquals(car.getVin(), result.getVin());
    }

    @Test
    public void getAllCarsTest() {
        //Given
        List<Car> carList = initCarList();
        when(carRepository.findAll()).thenReturn(carList);

        //When
        List<Car> resultList = carService.getCars();

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
    }

    @Test
    public void getCarsByBrandTest() {
        //Given
        List<Car> carList = initCarList();
        when(carRepository.findAllByBrand("Audi")).thenReturn(carList);

        //When
        List<Car> resultList = carService.getCarsByBrand("Audi");

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(carList.get(0).getBrand(), resultList.get(0).getBrand());
        assertEquals(carList.get(1).getBrand(), resultList.get(1).getBrand());
    }

    @Test
    public void getCarsByFuelTypeTest() {
        //Given
        List<Car> carList = initCarList();
        when(carRepository.findAllByFuelType("Diesel")).thenReturn(carList);

        //When
        List<Car> resultList = carService.getCarsByFuelType("Diesel");

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(carList.get(0).getFuelType(), resultList.get(0).getFuelType());
        assertEquals(carList.get(1).getFuelType(), resultList.get(1).getFuelType());
    }

    @Test
    public void getCarsByBodyClassTypeTest() {
        //Given
        List<Car> carList = initCarList();
        when(carRepository.findAllByBodyClass("Saloon")).thenReturn(carList);

        //When
        List<Car> resultList = carService.getCarsByBodyClass("Saloon");

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(carList.get(0).getBodyClass(), resultList.get(0).getBodyClass());
        assertEquals(carList.get(1).getBodyClass(), resultList.get(1).getBodyClass());
    }

    @Test
    public void getCarsByCostPerDayLessThenTest() {
        //Given
        List<Car> carList = initCarList();
        when(carRepository.findAllByCostPerDayLessThan(new BigDecimal(40))).thenReturn(carList);

        //When
        List<Car> resultList = carService.getCarsByCostPerDayLessThan(new BigDecimal(40));

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(carList.get(0).getBodyClass(), resultList.get(0).getBodyClass());
        assertEquals(carList.get(1).getBodyClass(), resultList.get(1).getBodyClass());
    }

    @Test
    public void getCarsByMileageLessThenTest() {
        //Given
        List<Car> carList = initCarList();
        when(carRepository.findAllByMileageLessThan(300000)).thenReturn(carList);

        //When
        List<Car> resultList = carService.getCarsByMileageLessThen(300000);

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(carList.get(0).getBodyClass(), resultList.get(0).getBodyClass());
        assertEquals(carList.get(1).getBodyClass(), resultList.get(1).getBodyClass());
    }

    @Test
    public void deleteCarTest() {
        //Given
        //When
        carService.deleteCar(2L);

        //Then
        verify(carRepository, times(1)).deleteById(2L);
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
                110000,
                new BigDecimal(18));
    }

    private List<Car> initCarList() {
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
                "Audi",
                "A6",
                2015,
                "Diesel",
                3.0,
                "Saloon",
                250000,
                new BigDecimal(25));

        return Arrays.asList(car1, car2);
    }
}
