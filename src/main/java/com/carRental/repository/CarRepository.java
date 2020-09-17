package com.carRental.repository;

import com.carRental.domain.Car;
import com.carRental.domain.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    @Override
    List<Car> findAll();

    List<Car> findAllByBrand(String brand);

    List<Car> findAllByFuelType(String fuelType);

    List<Car> findAllByBodyClass(String bodyClass);

    List<Car> findAllByMileageLessThan(int maxDistance);

    List<Car> findAllByCostPerDayLessThan(BigDecimal maxCost);

    Optional<Car> findByVin(String vin);

    long countAllByStatus(Status status);
}

