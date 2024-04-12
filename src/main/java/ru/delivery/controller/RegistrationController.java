package ru.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.delivery.security.RegistrationForm;
import ru.delivery.service.RegistrationService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {


    private final RegistrationService service;

    @Autowired
    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @ModelAttribute("registrationForm")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

    @GetMapping
    public String view() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form,
                                      Errors errors) {
        service.registerCustomer(form, errors);

        return "redirect:/login";
    }
}
