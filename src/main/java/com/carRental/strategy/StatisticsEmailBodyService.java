package com.carRental.strategy;

import com.carRental.domain.Status;
import com.carRental.repository.CarRepository;
import com.carRental.repository.RentalRepository;
import com.carRental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsEmailBodyService implements EmailBodyService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public StatisticsEmailBodyService(CarRepository carRepository, UserRepository userRepository,
                                      RentalRepository rentalRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    @Override
    public String emailBodyCreate() {
        long userRepositorySize = userRepository.count();
        long carRentedSize = carRepository.countAllByStatus(Status.RENTED);
        long carAvailableSize = carRepository.countAllByStatus(Status.AVAILABLE);
        long rentalRepositorySize = rentalRepository.count();

        return ("\n Dear car rental administrator." +
                "\n\t Below You can find daily statistics considering Your database: \n" +
                "\n\t Current number of registered users: " + userRepositorySize +
                "\n\t Current number of rented cars: " + carRentedSize +
                "\n\t Current number of available cars: " + carAvailableSize +
                "\n\t Current number of all rentals: " + rentalRepositorySize + "\n" +
                "\n Have a nice day!");
    }
}
