package com.carRental.facade;

import com.carRental.domain.Car;
import com.carRental.domain.Rental;
import com.carRental.domain.User;
import com.carRental.domain.dto.RentalComplexDto;
import com.carRental.domain.dto.RentalDto;
import com.carRental.domain.dto.RentalExtensionDto;
import com.carRental.exceptions.CarNotFoundException;
import com.carRental.exceptions.RentalNotFoundException;
import com.carRental.exceptions.UserNotFoundException;
import com.carRental.mapper.RentalMapper;
import com.carRental.repository.RentalRepository;
import com.carRental.service.RentalService;
import com.carRental.service.emailService.EmailToUsersService;
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
public class RentalFacadeTestSuite {

    @InjectMocks
    private RentalFacade rentalFacade;

    @Mock
    private RentalMapper rentalMapper;

    @Mock
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private EmailToUsersService emailToUsersService;

    @Test
    public void getRentalByIdTest() throws RentalNotFoundException {
        //Given
        Rental rental = initRental();
        RentalComplexDto rentalComplexDto = initRentalComplexDto();

        when(rentalService.getRentalById(1L)).thenReturn(rental);
        when(rentalMapper.mapToRentalComplexDto(rental)).thenReturn(rentalComplexDto);

        //When
        RentalComplexDto result = rentalFacade.getRentalById(1L);

        //Then
        assertEquals(rentalComplexDto.getId(), result.getId());
    }

    @Test
    public void getRentalsTest() {
        //Given
        Rental rental = initRental();
        RentalComplexDto rentalComplexDto = initRentalComplexDto();
        List<Rental> rentalList = Collections.singletonList(rental);
        List<RentalComplexDto> rentalComplexDtoList = Collections.singletonList(rentalComplexDto);

        when(rentalService.getRentals()).thenReturn(rentalList);
        when(rentalMapper.mapToRentalComplexDtoList(rentalList)).thenReturn(rentalComplexDtoList);

        //When
        List<RentalComplexDto> resultList = rentalFacade.getRentals();

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        resultList.forEach(r -> {
            assertEquals(r.getId(), rentalComplexDto.getId());
            assertEquals(r.getRentedFrom(), rentalComplexDto.getRentedFrom());
            assertEquals(r.getRentedTo(), rentalComplexDto.getRentedTo());
            assertEquals(r.getCarBrand(), rentalComplexDto.getCarBrand());
        });
    }

    @Test
    public void getRentalsByUserIdTest() {
        //Given
        Rental rental = initRental();
        rental.setId(1L);
        RentalComplexDto rentalComplexDto = initRentalComplexDto();
        List<Rental> rentalList = Collections.singletonList(rental);
        List<RentalComplexDto> rentalComplexDtoList = Collections.singletonList(rentalComplexDto);

        when(rentalService.getRentalsByUserId(1L)).thenReturn(rentalList);
        when(rentalMapper.mapToRentalComplexDtoList(rentalList)).thenReturn(rentalComplexDtoList);

        //When
        List<RentalComplexDto> filteredList = rentalFacade.getRentalsByUserId(1L);

        //Then
        assertNotNull(filteredList);
        assertEquals(1, filteredList.size());

        filteredList.forEach(r -> {
            assertEquals(r.getId(), rentalComplexDto.getId());
            assertEquals(r.getRentedFrom(), rentalComplexDto.getRentedFrom());
            assertEquals(r.getRentedTo(), rentalComplexDto.getRentedTo());
            assertEquals(r.getCarBrand(), rentalComplexDto.getCarBrand());
        });
    }

    @Test
    public void createRentalTest() throws CarNotFoundException, UserNotFoundException {
        //Given
        Rental rental = initRental();
        RentalDto rentalDto = initRentalDto();
        RentalComplexDto rentalComplexDto = initRentalComplexDto();

        when(rentalService.createRental(rentalDto)).thenReturn(rental);
        when(rentalMapper.mapToRentalComplexDto(rental)).thenReturn(rentalComplexDto);
        doNothing().when(emailToUsersService).sendEmailAboutRental(rental, "created");

        //When
        RentalComplexDto result = rentalFacade.createRental(rentalDto);

        //Then
        assertEquals(rentalComplexDto.getId(), result.getId());
        assertEquals(rentalComplexDto.getUserName(), result.getUserName());
        assertEquals(rentalComplexDto.getCarModel(), result.getCarModel());
        verify(emailToUsersService, times(1)).sendEmailAboutRental(rental, "created");
    }

    @Test
    public void modifyRentalTest() throws CarNotFoundException, UserNotFoundException, RentalNotFoundException {
        //Given
        Rental rental = initRental();
        RentalDto rentalDto = initRentalDto();
        RentalComplexDto rentalComplexDto = initRentalComplexDto();

        when(rentalService.modifyRental(rentalDto)).thenReturn(rental);
        when(rentalMapper.mapToRentalComplexDto(rental)).thenReturn(rentalComplexDto);
        doNothing().when(emailToUsersService).sendEmailAboutRental(rental, "modified");

        //When
        RentalComplexDto result = rentalFacade.modifyRental(rentalDto);

        //Then
        assertEquals(rentalComplexDto.getId(), result.getId());
        assertEquals(rentalComplexDto.getUserName(), result.getUserName());
        assertEquals(rentalComplexDto.getCarModel(), result.getCarModel());
        verify(emailToUsersService, times(1)).sendEmailAboutRental(rental, "modified");
    }

    @Test
    public void extendRentalTest() throws RentalNotFoundException {
        //Given
        Rental rental = initRental();
        RentalExtensionDto rentalExtensionDto = initRentalExtensionDto();

        RentalComplexDto extendedRentalComplexDto = new RentalComplexDto(
                1L,
                LocalDate.of(2020, 10, 10),
                LocalDate.of(2020, 10, 20),
                new BigDecimal(125),
                "Audi",
                "A3",
                "Jack",
                "Smith",
                "email",
                123456);

        when(rentalService.extendRental(rentalExtensionDto)).thenReturn(rental);
        when(rentalMapper.mapToRentalComplexDto(rental)).thenReturn(extendedRentalComplexDto);
        doNothing().when(emailToUsersService).sendEmailAboutRental(rental, "extended");

        //When
        RentalComplexDto result = rentalFacade.extendRental(rentalExtensionDto);

        //Then
        assertEquals(extendedRentalComplexDto.getId(), result.getId());
        assertEquals(extendedRentalComplexDto.getRentedTo(), result.getRentedTo());
        verify(emailToUsersService, times(1)).sendEmailAboutRental(rental, "extended");
    }

    @Test
    public void closeRentalTest() throws RentalNotFoundException {
        //Given
        Rental rental = initRental();

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        doNothing().when(emailToUsersService).sendEmailAboutRental(rental, "closed");

        //When
        rentalFacade.closeRental(1L);

        //Then
        assertEquals(LocalDate.now(), rental.getRentedTo());
        verify(rentalService, times(1)).closeRental(1L);
        verify(emailToUsersService, times(1)).sendEmailAboutRental(rental, "closed");
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

    private RentalComplexDto initRentalComplexDto() {
        return new RentalComplexDto(
                1L,
                LocalDate.of(2020, 10, 10),
                LocalDate.of(2020, 10, 15),
                new BigDecimal(125),
                "Audi",
                "A3",
                "Jack",
                "Smith",
                "email",
                123456);
    }
}




