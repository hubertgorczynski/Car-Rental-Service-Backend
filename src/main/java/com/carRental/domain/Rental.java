package com.carRental.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RENTALS")
public class Rental {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "RENTED_FROM")
    private LocalDate rentedFrom;

    @NotNull
    @Column(name = "RENTED_TO")
    private LocalDate rentedTo;

    @NotNull
    @Column(name = "DURATION")
    private long duration;

    @NotNull
    @Column(name = "COST")
    private double cost;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CAR_ID")
    private Car car;

    public Rental(LocalDate rentedFrom, LocalDate rentedTo, User user, Car car) {
        this.rentedFrom = rentedFrom;
        this.rentedTo = rentedTo;
        this.user = user;
        this.car = car;
        this.duration = DAYS.between(rentedFrom, rentedTo);
        this.cost = duration * car.getCostPerDay();
    }
}

