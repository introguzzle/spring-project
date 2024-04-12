package ru.delivery.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.delivery.entity.Customer;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String repeatPassword;

    private String street;
    private String city;
    private String zip;

    private String phoneNumber;


    public Customer toCustomer(PasswordEncoder encoder) {
        return Customer.builder()
                .username(username)
                .password(encoder.encode(password))
                .street(street)
                .city(city)
                .zip(zip)
                .phoneNumber(phoneNumber)
                .build();
    }
}
