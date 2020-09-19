package com.carRental.mapper;

import com.carRental.domain.Rental;
import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalEmailDto;
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

    public List<RentalEmailDto> mapToRentalEmailDtoList(final List<Rental> rentalList) {
        return rentalList.stream()
                .map(rental -> new RentalEmailDto(
                        rental.getId(),
                        rental.getRentedFrom(),
                        rental.getRentedTo(),
                        rental.getCost(),
                        rental.getCar().getBrand(),
                        rental.getCar().getModel(),
                        rental.getUser().getName(),
                        rental.getUser().getLastName(),
                        rental.getUser().getEmail(),
                        rental.getUser().getPhoneNumber()))
                .collect(Collectors.toList());
    }
}
