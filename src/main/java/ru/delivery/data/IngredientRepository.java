package ru.delivery.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.delivery.entity.Ingredient;

import java.util.List;


public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    Ingredient findIngredientByName(String name);
    Ingredient findIngredientByNameAndId(String name, String id);

    default void saveAll(List<? extends Ingredient> list) {
        this.saveAll((Iterable<? extends Ingredient>) list);
    }

    @Query("SELECT i FROM Ingredient i WHERE i.name = 'Clown'")
    Ingredient findCustomQuery();
}
