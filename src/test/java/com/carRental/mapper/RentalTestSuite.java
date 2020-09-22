package com.carRental.mapper;

import com.carRental.domain.Car;
import com.carRental.domain.Rental;
import com.carRental.domain.User;
import com.carRental.domain.dto.RentalComplexDto;
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
            new BigDecimal(25));

    User user = new User(
            1L,
            "Jack",
            "Smith",
            "email",
            "password",
            123456);

    Rental rental = new Rental(
            LocalDate.of(2020, 8, 20),
            LocalDate.of(2020, 8, 25),
            user,
            car);

    RentalComplexDto rentalComplexDto = new RentalComplexDto(
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

    @Test
    public void mapToRentalComplexDtoTest() {
        //Given
        rental.setId(1L);

        //When
        RentalComplexDto mappedRental = this.rentalMapper.mapToRentalComplexDto(rental);

        //Then
        assertEquals(mappedRental.getId(), rentalComplexDto.getId());
        assertEquals(mappedRental.getRentedFrom(), rentalComplexDto.getRentedFrom());
        assertEquals(mappedRental.getRentedTo(), rentalComplexDto.getRentedTo());
        assertEquals(mappedRental.getRentalCost(), rentalComplexDto.getRentalCost());
        assertEquals(mappedRental.getCarBrand(), rentalComplexDto.getCarBrand());
        assertEquals(mappedRental.getUserName(), rentalComplexDto.getUserName());
        assertEquals(mappedRental.getUserPhoneNumber(), rentalComplexDto.getUserPhoneNumber());
    }

    @Test
    public void mapToRentalComplexDtoListTest() {
        //Given
        rental.setId(1L);
        List<Rental> rentalList = Arrays.asList(rental);

        //When
        List<RentalComplexDto> mappedList = rentalMapper.mapToRentalComplexDtoList(rentalList);

        //Then
        assertNotNull(mappedList);
        assertEquals(1, mappedList.size());

        mappedList.forEach(r -> {
            assertEquals(r.getId(), rentalComplexDto.getId());
            assertEquals(r.getRentedFrom(), rentalComplexDto.getRentedFrom());
            assertEquals(r.getRentedTo(), rentalComplexDto.getRentedTo());
            assertEquals(r.getRentalCost(), rentalComplexDto.getRentalCost());
            assertEquals(r.getCarBrand(), rentalComplexDto.getCarBrand());
            assertEquals(r.getUserName(), rentalComplexDto.getUserName());
            assertEquals(r.getUserPhoneNumber(), rentalComplexDto.getUserPhoneNumber());
        });
    }
}
