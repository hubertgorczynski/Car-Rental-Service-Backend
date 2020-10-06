package com.carRental.service;

import com.carRental.domain.Login;
import com.carRental.exceptions.LoginNotFoundException;
import com.carRental.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean isLoginRegistered(String email, String password) {
        return loginRepository.existsByEmailAndPassword(email, password);
    }

    public Login getLoginByEmailAndPassword(String email, String password) throws LoginNotFoundException {
        return loginRepository.findByEmailAndPassword(email, password).orElseThrow(LoginNotFoundException::new);
    }

    public void saveLogin(Login login) {
        loginRepository.save(login);
    }

    public void deleteLogin(Login login) {
        loginRepository.delete(login);
    }
}
