package ru.delivery.service;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import ru.delivery.entity.Taco;
import ru.delivery.entity.TacoOrder;

public interface DesignService {
    boolean processTaco(Taco taco, Errors errors, TacoOrder tacoOrder);
    void addIngredientsToModel(Model model);
}
