package com.carRental.controller;

import com.carRental.domain.dto.CarDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(value = "/car")
    public CarDto getCarById(@RequestParam Long id) throws CarNotFoundException {
        return carService.getCarById(id);
    }

    @GetMapping(value = "/cars")
    public List<CarDto> getAllCars() {
        return carService.getCars();
    }

    @GetMapping(value = "/carsByBrand")
    public List<CarDto> getCarsByBrand(@RequestParam String brand) {
        return carService.getCarsByBrand(brand);
    }

    @PostMapping(value = "/car")
    public void createCar(@RequestBody CarDto carDto) {
        carService.saveCar(carDto);
    }

    @DeleteMapping(value = "/car")
    public void deleteCar(@RequestParam Long id) {
        carService.deleteCar(id);
    }
}
