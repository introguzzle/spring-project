package ru.delivery.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.delivery.entity.Admin;
import ru.delivery.entity.Customer;
import ru.delivery.entity.Ingredient;
import ru.delivery.entity.Taco;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Populate {

    public static class Loader implements CommandLineRunner {

        private final CustomerRepository customerRepository;
        private final IngredientRepository ingredientRepository;
        private final TacoRepository tacoRepository;
        private final PasswordEncoder passwordEncoder;

        public Loader(CustomerRepository customerRepository,
                      IngredientRepository ingredientRepository,
                      TacoRepository tacoRepository,
                      PasswordEncoder passwordEncoder) {

            this.customerRepository = customerRepository;
            this.ingredientRepository = ingredientRepository;
            this.tacoRepository = tacoRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) throws Exception {
            ingredientLoader(ingredientRepository).run(args);
            customerLoader(customerRepository, passwordEncoder).run(args);
            tacoLoader(ingredientRepository, tacoRepository).run(args);
        }
    }

    public static
    CommandLineRunner loader(CustomerRepository customerRepository,
                             IngredientRepository ingredientRepository,
                             TacoRepository tacoRepository,
                             PasswordEncoder passwordEncoder) {
        return new Loader(
                customerRepository,
                ingredientRepository,
                tacoRepository,
                passwordEncoder
        );
    }

    public static
    CommandLineRunner ingredientLoader(IngredientRepository ingredientRepository) {
        return args -> {
            ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
        };
    }

    public static
    CommandLineRunner customerLoader(CustomerRepository repository,
                                     PasswordEncoder encoder) {
        return args -> {
            repository.save(new Admin("admin", encoder.encode("1337")));
            repository.save(Customer.builder()
                            .username("test_user")
                            .password(encoder.encode("1337"))
                            .street("street")
                            .phoneNumber("+79991223344")
                            .city("ZZXXXXXXXXXXXXXXXXX")
                    .build());
        };
    }

    public static
    CommandLineRunner tacoLoader(IngredientRepository ingredientRepository,
                                 TacoRepository tacoRepository) {
        return args -> {
            Set<Ingredient> ingredients = new HashSet<>(
                    (Collection<? extends Ingredient>) ingredientRepository.findAll()
            );

            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(ingredients);
            tacoRepository.save(taco1);

            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(ingredients);
            tacoRepository.save(taco2);

            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(ingredients);
            tacoRepository.save(taco3);
        };
    }
}
