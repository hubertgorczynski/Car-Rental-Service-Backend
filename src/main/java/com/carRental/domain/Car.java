package com.carRental.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

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
    private double costPerDay;

    @NotNull
    @Column(name = "STATUS")
    private Status status;

    public Car(String vin, String brand, String model, int productionYear, String fuelType, double engineCapacity,
               String bodyClass, int mileage, double costPerDay, Status status) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.fuelType = fuelType;
        this.engineCapacity = engineCapacity;
        this.bodyClass = bodyClass;
        this.mileage = mileage;
        this.costPerDay = costPerDay;
        this.status = status;
    }
}
