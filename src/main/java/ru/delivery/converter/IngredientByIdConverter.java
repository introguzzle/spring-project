package ru.delivery.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.delivery.data.IngredientRepository;
import ru.delivery.entity.Ingredient;
import ru.delivery.entity.Ingredient.Type;

import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class
IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository repository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ingredient convert(String id)
            throws NoSuchElementException {

        return repository.findById(id).orElseThrow();
    }
}
