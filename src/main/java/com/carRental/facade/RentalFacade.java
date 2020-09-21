package com.carRental.facade;

import com.carRental.domain.dto.RentalComplexDto;
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

    public RentalComplexDto getRentalById(Long id) throws RentalNotFoundException {
        return rentalMapper.mapToRentalComplexDto(rentalService.getRentalById(id));
    }

    public List<RentalComplexDto> getRentals() {
        return rentalMapper.mapToRentalComplexDtoList(rentalService.getRentals());
    }

    public RentalComplexDto createRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        return rentalMapper.mapToRentalComplexDto(rentalService.createRental(rentalDto));
    }

    public RentalComplexDto modifyRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        return rentalMapper.mapToRentalComplexDto(rentalService.modifyRental(rentalDto));
    }

    public RentalComplexDto extendRental(RentalExtensionDto rentalExtensionDto) throws RentalNotFoundException {
        return rentalMapper.mapToRentalComplexDto(rentalService.extendRental(rentalExtensionDto));
    }

    public void closeRental(Long id) throws RentalNotFoundException {
        rentalService.closeRental(id);
    }
}
