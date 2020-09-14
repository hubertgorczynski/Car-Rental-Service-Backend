package com.carRental.service;

import com.carRental.domain.dto.CarDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.mapper.CarMapper;
import com.carRental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Autowired
    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public CarDto saveCar(final CarDto carDto) {
        return carMapper.mapToCarDto(carRepository.save(carMapper.mapToCar(carDto)));
    }

    public CarDto getCarById(final Long id) throws CarNotFoundException {
        return carMapper.mapToCarDto(carRepository.findById(id).orElseThrow(CarNotFoundException::new));
    }

    public CarDto getCarByVin(final String vin) throws CarNotFoundException {
        return carMapper.mapToCarDto(carRepository.findByVin(vin).orElseThrow(CarNotFoundException::new));
    }

    public List<CarDto> getCars() {
        return carMapper.mapToCarDtoList(carRepository.findAll());
    }

    public List<CarDto> getCarsByBrand(final String brand) {
        return carMapper.mapToCarDtoList(carRepository.findAllByBrand(brand));
    }

    public List<CarDto> getCarsByFuelType(final String fuelType) {
        return carMapper.mapToCarDtoList(carRepository.findAllByFuelType(fuelType));
    }

    public List<CarDto> getCarsByBodyClass(final String bodyClass) {
        return carMapper.mapToCarDtoList(carRepository.findAllByBodyClass(bodyClass));
    }

    public List<CarDto> getCarsByMileageLessThen(final int distance) {
        return carMapper.mapToCarDtoList(carRepository.findAllByMileageLessThan(distance));
    }

    public List<CarDto> getCarsByCostPerDayLessThan(final BigDecimal cost) {
        return carMapper.mapToCarDtoList(carRepository.findAllByCostPerDayLessThan(cost));
    }

    public void deleteCar(final Long id) {
        carRepository.deleteById(id);
    }
}
