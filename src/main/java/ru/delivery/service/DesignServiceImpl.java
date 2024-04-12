package ru.delivery.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.delivery.controller.Logging;
import ru.delivery.data.IngredientRepository;
import ru.delivery.entity.Ingredient;
import ru.delivery.entity.Taco;
import ru.delivery.entity.TacoOrder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DesignServiceImpl implements DesignService {

    private IngredientRepository repository;

    public DesignServiceImpl(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean processTaco(
            Taco taco, Errors errors,
            TacoOrder tacoOrder) {
        if (errors.hasErrors() || taco == null || tacoOrder == null) {
            reject(taco);
            return false;
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return true;
    }

    @Override
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = repository.findAll();

        for (Ingredient.Type type: Ingredient.Type.values()) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    private static void reject(Taco taco) {
        log.info("Taco processing rejected: {}", taco);
    }

    private static Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients,
                                              Ingredient.Type type) {
        List<Ingredient> filteredIngredients = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            if (ingredient.getType().equals(type)) {
                filteredIngredients.add(ingredient);
            }
        }

        return filteredIngredients;
    }
}
