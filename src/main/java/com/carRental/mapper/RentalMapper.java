package com.carRental.mapper;

import com.carRental.domain.Rental;
import com.carRental.domain.dto.RentalComplexDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public RentalComplexDto mapToRentalComplexDto(final Rental rental) {
        return new RentalComplexDto(
                rental.getId(),
                rental.getRentedFrom(),
                rental.getRentedTo(),
                rental.getCost(),
                rental.getCar().getId(),
                rental.getCar().getBrand(),
                rental.getCar().getModel(),
                rental.getUser().getName(),
                rental.getUser().getLastName(),
                rental.getUser().getEmail(),
                rental.getUser().getPhoneNumber());
    }

    public List<RentalComplexDto> mapToRentalComplexDtoList(final List<Rental> rentalList) {
        return rentalList.stream()
                .map(rental -> new RentalComplexDto(
                        rental.getId(),
                        rental.getRentedFrom(),
                        rental.getRentedTo(),
                        rental.getCost(),
                        rental.getCar().getId(),
                        rental.getCar().getBrand(),
                        rental.getCar().getModel(),
                        rental.getUser().getName(),
                        rental.getUser().getLastName(),
                        rental.getUser().getEmail(),
                        rental.getUser().getPhoneNumber()))
                .collect(Collectors.toList());
    }
}
