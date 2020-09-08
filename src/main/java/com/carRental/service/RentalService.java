package com.carRental.service;

import com.carRental.domain.Car;
import com.carRental.domain.Rental;
import com.carRental.domain.Status;
import com.carRental.domain.User;
import com.carRental.domain.dto.RentalDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.mapper.RentalMapper;
import com.carRental.repository.CarRepository;
import com.carRental.repository.RentalRepository;
import com.carRental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, RentalMapper rentalMapper, UserRepository userRepository,
                         CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public Rental createRental(final LocalDate rentedFrom, final LocalDate rentedTo, final Long userId, final Long carId)
            throws UserNotFoundException, CarNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(carId).orElseThrow(CarNotFoundException::new);

        car.setStatus(Status.RENTED);
        carRepository.save(car);

        Rental rental = new Rental(rentedFrom, rentedTo, user, car);
        return rentalRepository.save(rental);
    }

    public RentalDto getRentalById(Long id) throws RentalNotFoundException {
        return rentalMapper.mapToRentalDto(rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new));
    }

    public List<RentalDto> getRentals() {
        return rentalMapper.mapToRentalDtoList(rentalRepository.findAll());
    }

    public void closeRental(final Long id) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);

        rental.getUser().getRentals().remove(rental);
        rental.getCar().getRentals().remove(rental);
        rental.getCar().setStatus(Status.AVAILABLE);

        rentalRepository.deleteById(id);
    }
}
