package ru.delivery.service;

import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;

import ru.delivery.entity.TacoOrder;

import java.security.Principal;


public interface OrderService {
    boolean registerOrder(TacoOrder order,
                          Errors errors,
                          SessionStatus sessionStatus,
                          Principal principal);
}
