package com.carRental.service;

import com.carRental.domain.Car;
import com.carRental.exceptions.CarNotFoundException;
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

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car getCarById(Long id) throws CarNotFoundException {
        return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public Car getCarByVin(String vin) throws CarNotFoundException {
        return carRepository.findByVin(vin).orElseThrow(CarNotFoundException::new);
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public List<Car> getCarsByBrand(String brand) {
        return (carRepository.findAllByBrand(brand));
    }

    public List<Car> getCarsByFuelType(String fuelType) {
        return carRepository.findAllByFuelType(fuelType);
    }

    public List<Car> getCarsByBodyClass(String bodyClass) {
        return carRepository.findAllByBodyClass(bodyClass);
    }

    public List<Car> getCarsByMileageLessThen(int distance) {
        return carRepository.findAllByMileageLessThan(distance);
    }

    public List<Car> getCarsByCostPerDayLessThan(BigDecimal cost) {
        return carRepository.findAllByCostPerDayLessThan(cost);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
