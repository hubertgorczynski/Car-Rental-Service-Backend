package com.carRental.facade;

import com.carRental.domain.Rental;
import com.carRental.domain.dto.RentalComplexDto;
import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalExtensionDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.mapper.RentalMapper;
import com.carRental.repository.RentalRepository;
import com.carRental.service.RentalService;
import com.carRental.service.emailService.EmailToUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RentalFacade {

    private final RentalService rentalService;
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final EmailToUsersService emailToUsersService;

    @Autowired
    public RentalFacade(RentalService rentalService, RentalRepository rentalRepository, RentalMapper rentalMapper,
                        EmailToUsersService emailToUsersService) {
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
        this.emailToUsersService = emailToUsersService;
    }

    public RentalComplexDto getRentalById(Long id) throws RentalNotFoundException {
        return rentalMapper.mapToRentalComplexDto(rentalService.getRentalById(id));
    }

    public List<RentalComplexDto> getRentals() {
        return rentalMapper.mapToRentalComplexDtoList(rentalService.getRentals());
    }

    public RentalComplexDto createRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        Rental createdRental = rentalService.createRental(rentalDto);
        emailToUsersService.sendEmailAboutRental(createdRental, "created");
        return rentalMapper.mapToRentalComplexDto(createdRental);
    }

    public RentalComplexDto modifyRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException,
            RentalNotFoundException {
        Rental modifiedRental = rentalService.modifyRental(rentalDto);
        emailToUsersService.sendEmailAboutRental(modifiedRental, "modified");
        return rentalMapper.mapToRentalComplexDto(modifiedRental);
    }

    public RentalComplexDto extendRental(RentalExtensionDto rentalExtensionDto) throws RentalNotFoundException {
        Rental extendedRental = rentalService.extendRental(rentalExtensionDto);
        emailToUsersService.sendEmailAboutRental(extendedRental, "extended");
        return rentalMapper.mapToRentalComplexDto(extendedRental);
    }

    public void closeRental(Long id) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);

        rental.setRentedTo(LocalDate.now());
        rentalService.updateDuration(rental);
        rentalService.updateCost(rental);
        emailToUsersService.sendEmailAboutRental(rental, "closed");

        rentalService.closeRental(id);
    }
}
