package com.carRental.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOGINS")
public class Login {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
