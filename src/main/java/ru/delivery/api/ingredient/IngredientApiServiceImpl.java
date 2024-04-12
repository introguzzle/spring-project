package ru.delivery.api.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.delivery.entity.Ingredient;

import java.net.URI;
import java.util.Optional;

@Service
public class IngredientApiServiceImpl implements IngredientApiService {

    @Value("${spring.data.rest.base-path}")
    private String API;

    private String path() {
        return String.format("http://localhost:8080/%s/ingredients", API);
    }

    private String pathId() {
        return path() + "/{id}";
    }

    private final RestTemplate restTemplate;

    @Autowired
    public IngredientApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Ingredient> getIngredient(String id) {
        Ingredient ingredient;

        try {
            ingredient = restTemplate.getForObject(
                    pathId(),
                    Ingredient.class,
                    id
            );
        } catch (RestClientException e) {
            return Optional.empty();
        }

        return Optional.ofNullable(ingredient);
    }

    @Override
    public boolean putIngredient(Ingredient ingredient) {
        if (this.getIngredient(ingredient.getId()).isEmpty())
            return false;

        restTemplate.put(
                pathId(),
                ingredient,
                ingredient.getId()
        );

        return true;
    }

    @Override
    public boolean deleteIngredient(Ingredient ingredient) {
        restTemplate.delete(path(), ingredient.getId());
        return false;
    }

    @Override
    public boolean deleteIngredient(String id) {
        restTemplate.delete(pathId(), id);
        return false;
    }

    @Override
    public Optional<URI> postIngredient(Ingredient ingredient) {
        try {
            if (this.getIngredient(ingredient.getId()).isPresent())
                return Optional.empty();

            return Optional.ofNullable(restTemplate.postForLocation(
                    path(),
                    ingredient,
                    ingredient.getId()
            ));

        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
