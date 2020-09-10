package com.carRental.controller;

import com.carRental.domain.dto.CarDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) throws CarNotFoundException {
        return carService.getCarById(id);
    }

    @GetMapping("/{vin}")
    public CarDto getCarByVin(@PathVariable String vin) throws CarNotFoundException {
        return carService.getCarByVin(vin);
    }

    @GetMapping
    public List<CarDto> getAllCars(@RequestParam(required = false) String brand,
                                   @RequestParam(required = false) String fuelType,
                                   @RequestParam(required = false) String bodyClass) {
        if (brand != null && !brand.isEmpty()) {
            return carService.getCarsByBrand(brand);
        } else if (fuelType != null && !fuelType.isEmpty()) {
            return carService.getCarsByFuelType(fuelType);
        } else if (bodyClass != null && !bodyClass.isEmpty()) {
            return carService.getCarsByBodyClass(bodyClass);
        } else {
            return carService.getCars();
        }
    }

    @GetMapping("/by_mileage_less_then/{distance}")
    public List<CarDto> getAllCarsByMileageLessThen(@PathVariable int distance) {
        return carService.getCarsByMileageLessThen(distance);
    }

    @GetMapping("/by_cost_per_day_less_then/{cost}")
    public List<CarDto> getAllCarsByCostPerDayLessThan(@PathVariable BigDecimal cost) {
        return carService.getCarsByCostPerDayLessThan(cost);
    }

    @PostMapping
    public void createCar(@RequestBody CarDto carDto) {
        carService.saveCar(carDto);
    }

    @PutMapping
    public void modifyCar(@RequestBody CarDto carDto) {
        carService.saveCar(carDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
