package com.carRental.mapper;

import com.carRental.domain.Rental;
import com.carRental.domain.dto.RentalDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getRentedFrom(),
                rental.getRentedTo(),
                rental.getCar().getId(),
                rental.getUser().getId());
    }

    public List<RentalDto> mapToRentalDtoList(final List<Rental> rentalList) {
        return rentalList.stream()
                .map(rental -> new RentalDto(
                        rental.getId(),
                        rental.getRentedFrom(),
                        rental.getRentedTo(),
                        rental.getCar().getId(),
                        rental.getUser().getId()))
                .collect(Collectors.toList());
    }
}
