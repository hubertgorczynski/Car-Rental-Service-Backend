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

import java.time.LocalDate;
import java.util.List;

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
        carRepository.save(car);

        Rental rental = new Rental(
                rentalDto.getId(),
                rentalDto.getRentedFrom(),
                rentalDto.getRentedTo(),
                user,
                car);

        emailToUsersService.sendEmailAboutRental(rental, "created");
        return rentalRepository.save(rental);
    }

    public Rental modifyRental(RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        User user = userRepository.findById(rentalDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(rentalDto.getCarId()).orElseThrow(CarNotFoundException::new);

        Rental modifiedRental = new Rental(
                rentalDto.getId(),
                rentalDto.getRentedFrom(),
                rentalDto.getRentedTo(),
                user,
                car);

        emailToUsersService.sendEmailAboutRental(modifiedRental, "modified");
        return rentalRepository.save(modifiedRental);
    }

    public Rental extendRental(RentalExtensionDto rentalExtensionDto) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(rentalExtensionDto.getRentalId()).orElseThrow(RentalNotFoundException::new);

        LocalDate updateReturnDate = rental.getRentedTo().plusDays(rentalExtensionDto.getExtension());

        Rental extendedRental = new Rental(
                rentalExtensionDto.getRentalId(),
                rental.getRentedFrom(),
                updateReturnDate,
                rental.getUser(),
                rental.getCar());

        emailToUsersService.sendEmailAboutRental(extendedRental, "extended");
        return rentalRepository.save(extendedRental);
    }

    public void closeRental(Long id) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);
        Car car = rental.getCar();
        User user = rental.getUser();

        Rental closedRental = new Rental(
                id,
                rental.getRentedFrom(),
                LocalDate.now(),
                user,
                car);
        emailToUsersService.sendEmailAboutRental(closedRental, "closed");

        user.getRentals().remove(rental);
        car.getRentals().remove(rental);
        car.setStatus(Status.AVAILABLE);

        rentalRepository.deleteById(id);
    }
}
