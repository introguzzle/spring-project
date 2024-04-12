package ru.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.delivery.api.ingredient.IngredientApiService;
import ru.delivery.entity.Ingredient;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/ingredient_panel")
public class IngredientController {


    private final IngredientApiService service;

    @Autowired
    public IngredientController(IngredientApiService service) {
        this.service = service;
    }

    @ModelAttribute("ingredient")
    public Ingredient ingredient() {
        return new Ingredient();
    }

    @PostMapping
    public String process(Ingredient ingredient) {
        Optional<URI> uri = service.postIngredient(ingredient);

        return uri
                .map(value -> ("redirect:" + value.getPath()).replace("data-", ""))
                .orElse("Bad request");
    }

    @GetMapping
    public String view() {
        return "ingredient_panel";
    }


}
