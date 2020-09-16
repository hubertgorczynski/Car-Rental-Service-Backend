package com.carRental.facade;

import com.carRental.domain.dto.CarDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.mapper.CarMapper;
import com.carRental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CarFacade {

    private final CarService carService;
    private final CarMapper carMapper;

    @Autowired
    public CarFacade(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    public CarDto saveCar(CarDto carDto) {
        return carMapper.mapToCarDto(carService.saveCar(carMapper.mapToCar(carDto)));
    }

    public CarDto getCarById(Long id) throws CarNotFoundException {
        return carMapper.mapToCarDto(carService.getCarById(id));
    }

    public CarDto getCarByVin(String vin) throws CarNotFoundException {
        return carMapper.mapToCarDto(carService.getCarByVin(vin));
    }

    public List<CarDto> getCars() {
        return carMapper.mapToCarDtoList(carService.getCars());
    }

    public List<CarDto> getCarsByBrand(String brand) {
        return carMapper.mapToCarDtoList(carService.getCarsByBrand(brand));
    }

    public List<CarDto> getCarsByFuelType(String fuelType) {
        return carMapper.mapToCarDtoList(carService.getCarsByFuelType(fuelType));
    }

    public List<CarDto> getCarsByBodyClass(String bodyClass) {
        return carMapper.mapToCarDtoList(carService.getCarsByBodyClass(bodyClass));
    }

    public List<CarDto> getCarsByMileageLessThen(int distance) {
        return carMapper.mapToCarDtoList(carService.getCarsByMileageLessThen(distance));
    }

    public List<CarDto> getCarsByCostPerDayLessThan(BigDecimal cost) {
        return carMapper.mapToCarDtoList(carService.getCarsByCostPerDayLessThan(cost));
    }

    public void deleteCar(Long id) {
        carService.deleteCar(id);
    }
}


