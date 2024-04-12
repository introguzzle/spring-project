package ru.delivery.service;

import org.springframework.validation.Errors;
import ru.delivery.security.RegistrationForm;

public interface RegistrationService {

    void registerCustomer(RegistrationForm form, Errors errors);

}
