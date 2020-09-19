package com.carRental.strategy;

import com.carRental.domain.Rental;
import com.carRental.domain.dto.RentalEmailDto;
import com.carRental.mapper.RentalMapper;
import com.carRental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderEmailBodyService implements EmailBodyService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Autowired
    public ReminderEmailBodyService(RentalRepository rentalRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
    }

    @Override
    public String emailBodyCreate() {
        long rentalRepositorySize = rentalRepository.count();
        LocalDate today = LocalDate.now();

        List<Rental> closingDelayedRentalList = rentalRepository.findAllByRentedToAfter(today);
        List<Rental> closingSoonRentalList = rentalRepository.findAllByRentedToBefore(today.plusDays(3));

        List<RentalEmailDto> closingDelayedRentalDtoList = rentalMapper.mapToRentalEmailDtoList(closingDelayedRentalList);
        List<RentalEmailDto> closingSoonRentalDtoList = rentalMapper.mapToRentalEmailDtoList(closingSoonRentalList);

        return ("\n Dear car rental administrator." +
                "\n\t Below You can find daily reminder for currents rentals: \n" +
                "\n\t List of already delayed rentals: \n" +
                streamRentalListToString(closingDelayedRentalDtoList) +
                "\n\t List of soon closing rentals: \n" +
                streamRentalListToString(closingSoonRentalDtoList) +
                "\n\t Current number of all rentals: " + rentalRepositorySize + "\n" +
                "\n Have a nice day!");
    }

    private String streamRentalListToString(List<RentalEmailDto> rentalEmailDtoList) {
        return rentalEmailDtoList.stream()
                .map(RentalEmailDto::toString)
                .collect(Collectors.joining(",\n", "<<", ">>"));
    }
}
