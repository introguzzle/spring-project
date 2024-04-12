package ru.delivery.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;

import ru.delivery.entity.TacoOrder;
import ru.delivery.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    @Test
    void orderForm_ReturnsCorrectViewName() throws Exception {
        String viewName = orderController.orderForm();
        assertEquals("order_form", viewName);
    }

    @Test
    void processOrder_ValidOrder_SuccessfulRegistration() throws Exception {
        TacoOrder mockOrder = new TacoOrder();
        Errors mockErrors = mock(Errors.class);
        SessionStatus mockSessionStatus = mock(SessionStatus.class);

        // Установка ожиданий поведения мок-объектов
        when(orderService.registerOrder(mockOrder, mockErrors, mockSessionStatus, null)).thenReturn(true);

        String resultView = orderController.processOrder(
                mockOrder,
                mockErrors,
                mockSessionStatus,
                SecurityContextHolder.getContext().getAuthentication()
        );

        // Проверка, что был вызван метод registerOrder() с правильными параметрами
        verify(orderService).registerOrder(
                mockOrder,
                mockErrors,
                mockSessionStatus,
                null
        );

        // Проверка, что возвращается ожидаемое имя представления
        assertEquals("redirect:/", resultView);
    }
}