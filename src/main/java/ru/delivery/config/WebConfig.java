package ru.delivery.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import ru.delivery.data.CustomerRepository;
import ru.delivery.data.IngredientRepository;
import ru.delivery.data.Populate;
import ru.delivery.data.TacoRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    @Profile({"api"})
    public CommandLineRunner dataLoader(CustomerRepository customerRepository,
                                        IngredientRepository repository,
                                        TacoRepository tacoRepository,
                                        PasswordEncoder passwordEncoder) {
        return Populate.loader(
                customerRepository,
                repository,
                tacoRepository,
                passwordEncoder
        );
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        return restTemplate;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }
}
