package com.carRental.mapper;

import com.carRental.domain.Car;
import com.carRental.domain.Rental;
import com.carRental.domain.User;
import com.carRental.domain.dto.RentalComplexDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RentalMapperTestSuite {

    @Autowired
    private RentalMapper rentalMapper;

    @Test
    public void mapToRentalComplexDtoTest() {
        //Given
        Rental rental = initRental();
        rental.setId(1L);

        //When
        RentalComplexDto rentalComplexDto = rentalMapper.mapToRentalComplexDto(rental);

        //Then
        assertEquals(1L, (long) rentalComplexDto.getId());
        assertEquals(LocalDate.of(2020, 8, 20), rentalComplexDto.getRentedFrom());
        assertEquals(LocalDate.of(2020, 8, 25), rentalComplexDto.getRentedTo());
        assertEquals(new BigDecimal(125), rentalComplexDto.getRentalCost());
        assertEquals("Audi", rentalComplexDto.getCarBrand());
        assertEquals("A3", rentalComplexDto.getCarModel());
        assertEquals("Jack", rentalComplexDto.getUserName());
        assertEquals("Smith", rentalComplexDto.getUserLastName());
        assertEquals("email", rentalComplexDto.getUserEmail());
        assertEquals(123456, rentalComplexDto.getUserPhoneNumber());
    }

    @Test
    public void mapToRentalComplexDtoListTest() {
        //Given
        Rental rental = initRental();
        rental.setId(1L);
        List<Rental> rentalList = Collections.singletonList(rental);

        //When
        List<RentalComplexDto> mappedRentalComplexDtoList = rentalMapper.mapToRentalComplexDtoList(rentalList);

        //Then
        assertNotNull(mappedRentalComplexDtoList);
        assertEquals(1, mappedRentalComplexDtoList.size());

        assertEquals(1L, (long) mappedRentalComplexDtoList.get(0).getId());
        assertEquals(LocalDate.of(2020, 8, 20), mappedRentalComplexDtoList.get(0).getRentedFrom());
        assertEquals(LocalDate.of(2020, 8, 25), mappedRentalComplexDtoList.get(0).getRentedTo());
        assertEquals(new BigDecimal(125), mappedRentalComplexDtoList.get(0).getRentalCost());
        assertEquals("Audi", mappedRentalComplexDtoList.get(0).getCarBrand());
        assertEquals("A3", mappedRentalComplexDtoList.get(0).getCarModel());
        assertEquals("Jack", mappedRentalComplexDtoList.get(0).getUserName());
        assertEquals("Smith", mappedRentalComplexDtoList.get(0).getUserLastName());
        assertEquals("email", mappedRentalComplexDtoList.get(0).getUserEmail());
        assertEquals(123456, mappedRentalComplexDtoList.get(0).getUserPhoneNumber());
    }

    private Rental initRental() {
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

        return new Rental(
                LocalDate.of(2020, 8, 20),
                LocalDate.of(2020, 8, 25),
                user,
                car);
    }
}
