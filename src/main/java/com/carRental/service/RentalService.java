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
import com.carRental.mapper.RentalMapper;
import com.carRental.repository.CarRepository;
import com.carRental.repository.RentalRepository;
import com.carRental.repository.UserRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
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

    public RentalDto getRentalById(final Long id) throws RentalNotFoundException {
        return rentalMapper.mapToRentalDto(rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new));
    }

    public List<RentalDto> getRentals() {
        return rentalMapper.mapToRentalDtoList(rentalRepository.findAll());
    }

    public RentalDto createRental(final RentalDto rentalDto) throws UserNotFoundException, CarNotFoundException {
        User user = userRepository.findById(rentalDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(rentalDto.getCarId()).orElseThrow(CarNotFoundException::new);

        car.setStatus(Status.RENTED);
        carRepository.save(car);

        Rental rental = new Rental(
                rentalDto.getRentedFrom(),
                rentalDto.getRentedTo(),
                user,
                car);

        return rentalMapper.mapToRentalDto(rentalRepository.save(rental));
    }

    public RentalDto extendRental(final RentalExtensionDto rentalExtensionDto) throws RentalNotFoundException, CarNotFoundException, UserNotFoundException {
        Rental rental = rentalRepository.findById(rentalExtensionDto.getRentalId()).orElseThrow(RentalNotFoundException::new);
        User user = userRepository.findById(rentalExtensionDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Car car = carRepository.findById(rentalExtensionDto.getCarId()).orElseThrow(CarNotFoundException::new);

        LocalDate updateReturnDate = rental.getRentedTo().plusDays(rentalExtensionDto.getExtension());

        Rental updatedRental = new Rental(
                rental.getRentedFrom(),
                updateReturnDate,
                user,
                car);

        return rentalMapper.mapToRentalDto(rentalRepository.save(updatedRental));
    }

    public void closeRental(final Long id) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);

        rental.getUser().getRentals().remove(rental);
        rental.getCar().getRentals().remove(rental);
        rental.getCar().setStatus(Status.AVAILABLE);

        rentalRepository.deleteById(id);
    }
}
