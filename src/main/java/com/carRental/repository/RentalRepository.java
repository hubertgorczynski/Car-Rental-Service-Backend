package com.carRental.repository;

import com.carRental.domain.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

    @Override
    List<Rental> findAll();

    @Override
    Optional<Rental> findById(Long id);

    @Override
    Rental save(Rental rental);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}
