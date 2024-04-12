package ru.delivery.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.delivery.entity.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>,
        CrudRepository<Taco, Long> {
}
