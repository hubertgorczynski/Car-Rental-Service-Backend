package com.carRental.domain.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RentalEmailDto {
    private Long id;
    private LocalDate rentedFrom;
    private LocalDate rentedTo;
    private BigDecimal rentalCost;
    private String carBrand;
    private String carModel;
    private String userName;
    private String userLastName;
    private String userEmail;
    private int userPhoneNumber;
}
