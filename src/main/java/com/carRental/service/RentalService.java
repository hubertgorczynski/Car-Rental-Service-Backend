package com.carRental.service;

import com.carRental.domain.Car;
import com.carRental.domain.Rental;
import com.carRental.domain.Status;
import com.carRental.domain.User;
import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalExtensionDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.repository.CarRepository;
import com.carRental.repository.RentalRepository;
import com.carRental.repository.UserRepository;
import com.carRental.service.emailService.EmailToUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Transactional
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final EmailToUsersService emailToUsersService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, CarRepository carRepository,
                         EmailToUsersService emailToUsersService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.emailToUsersService = emailToUsersService;
    }

    public Rental getRentalById(Long id) throws RentalNotFoundException {
        return rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental createRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        User user = userRepository.findById(rentalDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(rentalDto.getCarId()).orElseThrow(CarNotFoundException::new);
        car.setStatus(Status.RENTED);

        Rental rental = new Rental(
                rentalDto.getRentedFrom(),
                rentalDto.getRentedTo(),
                user,
                car);

        Rental savedRental = rentalRepository.save(rental);
        emailToUsersService.sendEmailAboutRental(savedRental, "created");
        return savedRental;
    }

    public Rental modifyRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException, RentalNotFoundException {
        User user = userRepository.findById(rentalDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(rentalDto.getCarId()).orElseThrow(CarNotFoundException::new);
        Rental rental = rentalRepository.findById(rentalDto.getId()).orElseThrow(RentalNotFoundException::new);

        rental.setUser(user);
        rental.setCar(car);
        rental.setRentedFrom(rentalDto.getRentedFrom());
        rental.setRentedTo(rentalDto.getRentedTo());
        updateDuration(rental);
        updateCost(rental);

        emailToUsersService.sendEmailAboutRental(rental, "modified");
        return rental;
    }

    public Rental extendRental(RentalExtensionDto rentalExtensionDto) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(rentalExtensionDto.getRentalId()).orElseThrow(RentalNotFoundException::new);

        LocalDate updatedReturnDate = rental.getRentedTo().plusDays(rentalExtensionDto.getExtension());
        rental.setRentedTo(updatedReturnDate);
        updateDuration(rental);
        updateCost(rental);

        emailToUsersService.sendEmailAboutRental(rental, "extended");
        return rental;
    }

    public void closeRental(Long id) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);

        rental.setRentedTo(LocalDate.now());
        updateDuration(rental);
        updateCost(rental);
        emailToUsersService.sendEmailAboutRental(rental, "closed");

        rental.getUser().getRentals().remove(rental);
        rental.getCar().getRentals().remove(rental);
        rental.getCar().setStatus(Status.AVAILABLE);

        rentalRepository.deleteById(id);
    }

    private void updateDuration(Rental rental) {
        if (rental.getRentedTo().isAfter(rental.getRentedFrom())) {
            rental.setDuration(DAYS.between(rental.getRentedFrom(), rental.getRentedTo()));
        } else {
            rental.setDuration(0L);
        }
    }

    private void updateCost(Rental rental) {
        BigDecimal updatedCost = rental.getCar().getCostPerDay().multiply(new BigDecimal(rental.getDuration()));
        rental.setCost(updatedCost);
    }
}
