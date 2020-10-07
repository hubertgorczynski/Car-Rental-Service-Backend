package com.carRental.repository;

import com.carRental.domain.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {

    Boolean existsByEmailAndPassword(String email, String password);

    Optional<Login> findByEmailAndPassword(String email, String password);
}
