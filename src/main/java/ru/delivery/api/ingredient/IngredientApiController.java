package ru.delivery.api.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.delivery.data.IngredientRepository;
import ru.delivery.entity.Ingredient;

import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin("http:/localhost:8080")
@RequestMapping(path = "/api/ingredients", produces = "application/json")
public class IngredientApiController {

    private final IngredientApiService service;

    @Autowired
    public IngredientApiController(IngredientApiService service) {
        this.service = service;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getIngredient(@PathVariable String id) {
        Optional<Ingredient> ingredient = service.getIngredient(id);

        return ingredient
                .map(i -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(i))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateIngredient(@RequestBody Ingredient ingredient) {
        if (service.putIngredient(ingredient))
            return new ResponseEntity<>(ingredient, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<?> addIngredient(@RequestBody Ingredient ingredient) {
        return service.postIngredient(ingredient).isPresent()
                ? new ResponseEntity<>(ingredient, HttpStatus.CREATED)
                : new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@RequestBody Ingredient ingredient) {
        service.deleteIngredient(ingredient);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable String id) {
        service.deleteIngredient(id);
    }
}
