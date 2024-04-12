package ru.delivery.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;

import ru.delivery.controller.Logging;
import ru.delivery.data.OrderRepository;
import ru.delivery.entity.Customer;
import ru.delivery.entity.TacoOrder;

import java.security.Principal;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean registerOrder(TacoOrder order,
                                 @Nullable Errors errors,
                                 @Nullable SessionStatus sessionStatus,
                                 @Nullable Principal principal) {
        order.setCustomer((Customer) (principal));

        if (errors != null && errors.hasErrors()) {
            Logging.logErrors(log, errors);

            log.info("Order rejected: {}", order);

            return false;
        }

        log.info("Order submitted: {}", order);

        orderRepository.save(order);

        if (sessionStatus != null)
            sessionStatus.setComplete();

        return true;
    }
}
