package ru.delivery.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.delivery.data.OrderRepository;
import ru.delivery.entity.TacoOrder;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OrderControllerSpringTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderController orderController;

    private static TacoOrder mockOrder() {
        TacoOrder mockOrder = new TacoOrder();

        mockOrder.setCustomer(null);
        mockOrder.setDeliveryName("TEST NAME");
        mockOrder.setDeliveryCity("TEST CITY");
        mockOrder.setDeliveryStreet("TEST STREET");
        mockOrder.setDeliveryZipCode("TEST ZIP");

        mockOrder.setCcNumber("1111222233334444");
        mockOrder.setCcExpiration("01/29");
        mockOrder.setCcCVV("111");

        mockOrder.setTacos(null);

        return mockOrder;
    }

    @Test
    public void testProcessOrder() {
        TacoOrder mockOrder = mockOrder();

        orderController.processOrder(
                mockOrder,
                null,
                null,
                null
        );

        TacoOrder savedOrder = orderRepository.findByDeliveryName(
                mockOrder().getDeliveryName());

        assertNotNull(savedOrder);
        assertEquals(mockOrder.getDeliveryName(), savedOrder.getDeliveryName());
    }
}
