package com.carRental.repository;

import com.carRental.domain.Car;
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

    @Override
    Optional<Car> findById(Long id);

    Optional<Car> findByVin(String vin);

    @Override
    Car save(Car car);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}

