package com.carRental.controller;

import com.carRental.domain.dto.CarDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.facade.CarFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/cars")
public class CarController {

    private final CarFacade carFacade;

    @Autowired
    public CarController(CarFacade carFacade) {
        this.carFacade = carFacade;
    }

    @GetMapping("by_id/{id}")
    public CarDto getCarById(@PathVariable Long id) throws CarNotFoundException {
        return carFacade.getCarById(id);
    }

    @GetMapping("by_vin/{vin}")
    public CarDto getCarByVin(@PathVariable String vin) throws CarNotFoundException {
        return carFacade.getCarByVin(vin);
    }

    @GetMapping
    public List<CarDto> getAllCars(@RequestParam(required = false) String brand,
                                   @RequestParam(required = false) String fuelType,
                                   @RequestParam(required = false) String bodyClass) {
        if (brand != null && !brand.isEmpty()) {
            return carFacade.getCarsByBrand(brand);
        } else if (fuelType != null && !fuelType.isEmpty()) {
            return carFacade.getCarsByFuelType(fuelType);
        } else if (bodyClass != null && !bodyClass.isEmpty()) {
            return carFacade.getCarsByBodyClass(bodyClass);
        } else {
            return carFacade.getCars();
        }
    }

    @GetMapping("/by_mileage_less_then/{distance}")
    public List<CarDto> getAllCarsByMileageLessThen(@PathVariable int distance) {
        return carFacade.getCarsByMileageLessThen(distance);
    }

    @GetMapping("/by_cost_per_day_less_then/{cost}")
    public List<CarDto> getAllCarsByCostPerDayLessThan(@PathVariable BigDecimal cost) {
        return carFacade.getCarsByCostPerDayLessThan(cost);
    }

    @PostMapping
    public CarDto createCar(@RequestBody CarDto carDto) {
        return carFacade.saveCar(carDto);
    }

    @PutMapping
    public CarDto modifyCar(@RequestBody CarDto carDto) {
        return carFacade.saveCar(carDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carFacade.deleteCar(id);
    }
}
