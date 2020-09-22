package com.carRental.repository;

import com.carRental.domain.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

    @Override
    List<Rental> findAll();

    List<Rental> findAllByRentedToBetween(LocalDate startDate, LocalDate endDate);

    List<Rental> findAllByRentedToBefore(LocalDate date);
}
