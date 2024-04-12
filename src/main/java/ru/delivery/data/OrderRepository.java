package ru.delivery.data;


import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ru.delivery.entity.TacoOrder;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByDeliveryZipCode(String deliveryCode);
    List<TacoOrder> readTacoOrdersByDeliveryZipCodeAndPlacedAtBetween(
            String deliveryCode,
            Date from,
            Date to
    );


    @Query("SELECT o FROM TacoOrder o WHERE o.deliveryCity='Seattle'")
    List<TacoOrder> readTacoOrdersDeliveredInSeattle();

    TacoOrder findByDeliveryName(@NotBlank(message = "Delivery name is required")
                                 String deliveryName);
}
