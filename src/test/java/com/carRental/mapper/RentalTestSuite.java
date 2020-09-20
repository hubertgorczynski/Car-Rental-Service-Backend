package com.carRental.mapper;

import com.carRental.domain.Car;
import com.carRental.domain.Rental;
import com.carRental.domain.Status;
import com.carRental.domain.User;
import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalEmailDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class RentalTestSuite {

    @InjectMocks
    private RentalMapper rentalMapper;

    Car car = new Car(
            1L,
            "sampleVin",
            "Audi",
            "A3",
            2015,
            "Diesel",
            3.0,
            "Saloon",
            250000,
            new BigDecimal(25),
            Status.AVAILABLE);

    User user = new User(
            1L,
            "Jack",
            "Smith",
            "email",
            "password",
            123456,
            LocalDate.now());

    Rental rental = new Rental(
            1L,
            LocalDate.of(2020, 8, 20),
            LocalDate.of(2020, 8, 25),
            5L,
            new BigDecimal(125),
            user,
            car);

    RentalDto rentalDto = new RentalDto(
            1L,
            LocalDate.of(2020, 8, 20),
            LocalDate.of(2020, 8, 25),
            car.getId(),
            user.getId());

    List<Rental> rentalList = Arrays.asList(rental);

    @Test
    public void mapToRentalDtoTest() {
        //Given

        //When
        RentalDto mappedRentalDto = rentalMapper.mapToRentalDto(rental);

        //Then
        assertEquals(mappedRentalDto.getId(), rentalDto.getId());
        assertEquals(mappedRentalDto.getRentedFrom(), rentalDto.getRentedFrom());
        assertEquals(mappedRentalDto.getRentedTo(), rentalDto.getRentedTo());
        assertEquals(mappedRentalDto.getCarId(), rentalDto.getCarId());
        assertEquals(mappedRentalDto.getUserId(), rentalDto.getUserId());
    }

    @Test
    public void mapToRentalDtoListTest() {
        //Given

        //When
        List<RentalDto> mappedList = rentalMapper.mapToRentalDtoList(rentalList);

        //Then
        assertNotNull(mappedList);
        assertEquals(1, mappedList.size());

        mappedList.forEach(r -> {
            assertEquals(r.getId(), rentalDto.getId());
            assertEquals(r.getRentedFrom(), rentalDto.getRentedFrom());
            assertEquals(r.getRentedTo(), rentalDto.getRentedTo());
            assertEquals(r.getCarId(), rentalDto.getCarId());
            assertEquals(r.getUserId(), rentalDto.getUserId());
        });
    }

    @Test
    public void mapToRentalEmailDtoListTest() {
        //Given
        RentalEmailDto rentalEmailDto = new RentalEmailDto(
                1L,
                LocalDate.of(2020, 8, 20),
                LocalDate.of(2020, 8, 25),
                new BigDecimal(125),
                "Audi",
                "A3",
                "Jack",
                "Smith",
                "email",
                123456);

        //When
        List<RentalEmailDto> mappedList = rentalMapper.mapToRentalEmailDtoList(rentalList);

        //Then
        assertNotNull(mappedList);
        assertEquals(1, mappedList.size());

        mappedList.forEach(r -> {
            assertEquals(r.getId(), rentalEmailDto.getId());
            assertEquals(r.getRentedFrom(), rentalEmailDto.getRentedFrom());
            assertEquals(r.getRentedTo(), rentalEmailDto.getRentedTo());
            assertEquals(r.getRentalCost(), rentalEmailDto.getRentalCost());
            assertEquals(r.getCarBrand(), rentalEmailDto.getCarBrand());
            assertEquals(r.getUserName(), rentalEmailDto.getUserName());
            assertEquals(r.getUserPhoneNumber(), rentalEmailDto.getUserPhoneNumber());
        });
    }
}
