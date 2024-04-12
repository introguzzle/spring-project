package ru.delivery.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.delivery.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByUsername(String name);
}
