package com.carRental.facade;

import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalExtensionDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.mapper.RentalMapper;
import com.carRental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalFacade {

    private final RentalService rentalService;
    private final RentalMapper rentalMapper;

    @Autowired
    public RentalFacade(RentalService rentalService, RentalMapper rentalMapper) {
        this.rentalService = rentalService;
        this.rentalMapper = rentalMapper;
    }

    public RentalDto getRentalById(Long id) throws RentalNotFoundException {
        return rentalMapper.mapToRentalDto(rentalService.getRentalById(id));
    }

    public List<RentalDto> getRentals() {
        return rentalMapper.mapToRentalDtoList(rentalService.getRentals());
    }

    public RentalDto createRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        return rentalMapper.mapToRentalDto(rentalService.createRental(rentalDto));
    }

    public RentalDto extendRental(RentalExtensionDto rentalExtensionDto) throws RentalNotFoundException {
        return rentalMapper.mapToRentalDto(rentalService.extendRental(rentalExtensionDto));
    }

    public void closeRental(Long id) throws RentalNotFoundException {
        rentalService.closeRental(id);
    }
}
