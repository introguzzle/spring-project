package ru.delivery.api.ingredient;


import ru.delivery.entity.Ingredient;

import java.net.URI;
import java.util.Optional;

public interface IngredientApiService {

    Optional<Ingredient> getIngredient(String id);

    boolean putIngredient(Ingredient ingredient);

    boolean deleteIngredient(Ingredient ingredient);

    boolean deleteIngredient(String id);

    Optional<URI> postIngredient(Ingredient ingredient);

}
