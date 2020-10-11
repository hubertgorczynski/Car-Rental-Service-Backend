package com.carRental.service;

import com.carRental.domain.Car;
import com.carRental.domain.Rental;
import com.carRental.domain.User;
import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalExtensionDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.repository.CarRepository;
import com.carRental.repository.RentalRepository;
import com.carRental.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RentalServiceTestSuite {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Test
    public void createRentalTest() throws CarNotFoundException, UserNotFoundException {
        //Given
        User user = initUser();
        Car car = initCar();
        Rental rental = initRental();
        RentalDto rentalDto = initRentalDto();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        when(rentalRepository.save(any())).thenReturn(rental);

        //When
        Rental createdRental = rentalService.createRental(rentalDto);

        //Then
        assertEquals(createdRental.getCar().getBrand(), car.getBrand());
        assertEquals(createdRental.getUser().getName(), user.getName());
        assertEquals(createdRental.getRentedFrom(), LocalDate.of(2020, 10, 10));
        assertEquals(createdRental.getRentedTo(), LocalDate.of(2020, 10, 15));
    }

    @Test
    public void modifyRentalTest() throws UserNotFoundException, RentalNotFoundException, CarNotFoundException {
        //Given
        User user = initUser();
        Car car = initCar();
        Rental rental = initRental();
        RentalDto rentalDto = initRentalDto();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        //When
        Rental modifiedRental = rentalService.modifyRental(rentalDto);

        //Then
        assertEquals(rentalDto.getUserId(), modifiedRental.getUser().getId());
        assertEquals(rentalDto.getCarId(), modifiedRental.getCar().getId());
        assertEquals(rentalDto.getRentedFrom(), modifiedRental.getRentedFrom());
        assertEquals(rentalDto.getRentedTo(), modifiedRental.getRentedTo());
    }

    @Test
    public void extendRentalTest() throws RentalNotFoundException {
        //Given
        Rental rental = initRental();
        RentalExtensionDto rentalExtensionDto = initRentalExtensionDto();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        //When
        Rental extendedRental = rentalService.extendRental(rentalExtensionDto);

        //Then
        assertEquals(extendedRental.getRentedFrom(), LocalDate.of(2020, 10, 10));
        assertEquals(extendedRental.getRentedTo(), LocalDate.of(2020, 10, 20));
    }

    @Test
    public void getRentalByIdTest() throws RentalNotFoundException {
        //Given
        Rental rental = initRental();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        //When
        Rental result = rentalService.getRentalById(1L);

        //Then
        assertEquals(result.getId(), result.getId());
    }

    @Test
    public void getRentalsTest() {
        //Given
        Rental rental = initRental();
        List<Rental> rentalList = Collections.singletonList(rental);

        when(rentalRepository.findAll()).thenReturn(rentalList);

        //When
        List<Rental> resultList = rentalService.getRentals();

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
    }

    @Test
    public void getRentalsByUserIdTest() {
        //Given
        Rental rental = initRental();
        rental.setId(1L);
        List<Rental> rentalList = Collections.singletonList(rental);

        when(rentalService.getRentals()).thenReturn(rentalList);

        //When
        List<Rental> filteredList = rentalService.getRentalsByUserId(1L);

        //Then
        assertNotNull(filteredList);
        assertEquals(1, filteredList.size());
        assertEquals(1L, (long) filteredList.get(0).getId());
    }

    @Test
    public void closeRentalTest() throws RentalNotFoundException {
        //Given
        Rental rental = initRental();
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        //When
        rentalService.closeRental(1L);

        //Then
        verify(rentalRepository, times(1)).deleteById(1L);
    }

    @Test
    public void updateDurationTest() {
        //Given
        Rental rental = initRental();
        rental.setRentedTo(LocalDate.of(2020, 10, 28));

        //When
        rentalService.updateDuration(rental);

        //Then
        assertEquals(18L, (long) rental.getDuration());
    }

    @Test
    public void updateCOstTest() {
        //Given
        Rental rental = initRental();
        rental.setDuration(10L);

        //When
        rentalService.updateCost(rental);

        //Then
        assertEquals(new BigDecimal(180), rental.getCost());
    }

    private Car initCar() {
        return new Car(
                1L,
                "sampleVin",
                "Audi",
                "A3",
                2015,
                "Diesel",
                3.0,
                "Saloon",
                110000,
                new BigDecimal(18));
    }

    private User initUser() {
        return new User(
                1L,
                "Jack",
                "Smith",
                "email",
                "password",
                123456);
    }

    private Rental initRental() {
        User user = initUser();
        Car car = initCar();

        return new Rental(
                LocalDate.of(2020, 10, 10),
                LocalDate.of(2020, 10, 15),
                user,
                car);
    }

    private RentalDto initRentalDto() {
        return new RentalDto(
                1L,
                LocalDate.of(2020, 10, 10),
                LocalDate.of(2020, 10, 15),
                1L,
                1L);
    }

    private RentalExtensionDto initRentalExtensionDto() {
        return new RentalExtensionDto(1L, 5);
    }
}


