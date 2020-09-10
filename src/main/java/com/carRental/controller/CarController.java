package com.carRental.controller;

import com.carRental.domain.dto.CarDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(value = "/{id}")
    public CarDto getCarById(@PathVariable Long id) throws CarNotFoundException {
        return carService.getCarById(id);
    }

    @GetMapping
    public List<CarDto> getAllCars() {
        return carService.getCars();
    }

    @GetMapping(value = "/ByBrand/{brand}")
    public List<CarDto> getCarsByBrand(@PathVariable String brand) {
        return carService.getCarsByBrand(brand);
    }

    @PostMapping
    public void createCar(@RequestBody CarDto carDto) {
        carService.saveCar(carDto);
    }

    @PutMapping
    public void modifyCar(@RequestBody CarDto carDto) {
        carService.saveCar(carDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
