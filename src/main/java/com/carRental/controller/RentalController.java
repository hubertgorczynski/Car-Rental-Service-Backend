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
@RequestMapping("/v1")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping(value = "/rental")
    public void createRental(@RequestParam LocalDate rentedFrom, @RequestParam LocalDate rentedTo,
                             @RequestParam Long userId, @RequestParam Long carId)
            throws CarNotFoundException, UserNotFoundException {
        rentalService.createRental(rentedFrom, rentedTo, userId, carId);
    }

    @GetMapping(value = "rental")
    public RentalDto getRentalById(@RequestParam Long id) throws RentalNotFoundException {
        return rentalService.getRentalById(id);
    }

    @GetMapping(value = "/rentals")
    public List<RentalDto> getAllRentals() {
        return rentalService.getRentals();
    }

    @DeleteMapping(value = "/rental")
    public void closeRental(@RequestParam Long id) throws RentalNotFoundException {
        rentalService.closeRental(id);
    }
}
