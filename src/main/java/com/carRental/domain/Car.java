package com.carRental.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS")
public class Car {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "VIN")
    private String vin;

    @NotNull
    @Column(name = "BRAND")
    private String brand;

    @NotNull
    @Column(name = "MODEL")
    private String model;

    @NotNull
    @Column(name = "PRODUCTION_YEAR")
    private int productionYear;

    @NotNull
    @Column(name = "FUEL_TYPE")
    private String fuelType;

    @NotNull
    @Column(name = "ENGINE_CAPACITY")
    private double engineCapacity;

    @NotNull
    @Column(name = "BODY_CLASS")
    private String bodyClass;

    @NotNull
    @Column(name = "MILEAGE")
    private int mileage;

    @NotNull
    @Column(name = "COST_PER_DAY")
    private BigDecimal costPerDay;

    @Enumerated
    @Column(name = "STATUS")
    private Status status;

    @OneToMany(targetEntity = Rental.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "car")
    private List<Rental> rentals = new ArrayList<>();

    public Car(String vin, String brand, String model, int productionYear, String fuelType, double engineCapacity,
               String bodyClass, int mileage, BigDecimal costPerDay) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.fuelType = fuelType;
        this.engineCapacity = engineCapacity;
        this.bodyClass = bodyClass;
        this.mileage = mileage;
        this.costPerDay = costPerDay;
        this.status = Status.AVAILABLE;
    }

    public Car(Long id, String vin, String brand, String model, int productionYear, String fuelType,
               double engineCapacity, String bodyClass, int mileage, BigDecimal costPerDay) {
        this.id = id;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.fuelType = fuelType;
        this.engineCapacity = engineCapacity;
        this.bodyClass = bodyClass;
        this.mileage = mileage;
        this.costPerDay = costPerDay;
    }
}
