package com.carRental.controller;

import com.carRental.domain.dto.RentalDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    public void createRental(@RequestBody LocalDate rentedFrom, @RequestBody LocalDate rentedTo,
                             @RequestBody Long userId, @RequestBody Long carId)
            throws CarNotFoundException, UserNotFoundException {
        rentalService.createRental(rentedFrom, rentedTo, userId, carId);
    }

    @GetMapping(value = "/{id}")
    public RentalDto getRentalById(@PathVariable Long id) throws RentalNotFoundException {
        return rentalService.getRentalById(id);
    }

    @GetMapping
    public List<RentalDto> getAllRentals() {
        return rentalService.getRentals();
    }

    @DeleteMapping(value = "/{id}")
    public void closeRental(@PathVariable Long id) throws RentalNotFoundException {
        rentalService.closeRental(id);
    }
}
