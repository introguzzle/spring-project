package ru.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ru.delivery.data.CustomerRepository;
import ru.delivery.entity.Admin;
import ru.delivery.security.RegistrationForm;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final CustomerRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public RegistrationServiceImpl(CustomerRepository repository,
                                   PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }


    @Override
    public void registerCustomer(RegistrationForm form,
                                 Errors errors) {
        if (errors != null && !validate(form))
            rejectMismatch(errors);

        repository.save(form.toCustomer(encoder));
    }

    private static void rejectMismatch(Errors errors) {
        errors.rejectValue(
                "repeatPassword",
                "password_mismatch",
                "Passwords do not match"
        );
    }

    private static boolean validate(RegistrationForm form) {
        String password = form.getPassword();
        String repeat   = form.getRepeatPassword();

        return password != null && repeat != null && password.equals(repeat);
    }
}
