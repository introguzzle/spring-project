package ru.delivery.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ru.delivery.entity.TacoOrder;
import ru.delivery.service.OrderService;

import java.security.Principal;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public String orderForm() {
        return "order_form";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               @Nullable Errors errors,
                               @Nullable SessionStatus sessionStatus,
                               @Nullable Principal principal) {
        if (!service.registerOrder(order, errors, sessionStatus, principal)) {
            return "order_form";
        }

        return "redirect:/";
    }
}
