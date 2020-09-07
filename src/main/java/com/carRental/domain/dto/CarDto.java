package com.carRental.domain.dto;

import com.carRental.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private String vin;
    private String brand;
    private String model;
    private int productionYear;
    private String fuelType;
    private double engineCapacity;
    private String bodyClass;
    private int mileage;
    private double costPerDay;
    private Status status;
}
