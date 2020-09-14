package com.carRental.controller;

import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalExtensionDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/{id}")
    public RentalDto getRentalById(@PathVariable Long id) throws RentalNotFoundException {
        return rentalService.getRentalById(id);
    }

    @GetMapping
    public List<RentalDto> getAllRentals() {
        return rentalService.getRentals();
    }

    @PostMapping
    public RentalDto createRental(@RequestBody RentalDto rentalDto) throws CarNotFoundException, UserNotFoundException {
        return rentalService.createRental(rentalDto);
    }

    @PutMapping
    public RentalDto extendRental(@RequestBody RentalExtensionDto rentalExtensionDto) throws CarNotFoundException,
            UserNotFoundException, RentalNotFoundException {
        return rentalService.extendRental(rentalExtensionDto);
    }

    @DeleteMapping("/{id}")
    public void closeRental(@PathVariable Long id) throws RentalNotFoundException {
        rentalService.closeRental(id);
    }
}
