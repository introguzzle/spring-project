package ru.delivery.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import ru.delivery.entity.Taco;
import ru.delivery.entity.TacoOrder;
import ru.delivery.service.DesignService;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final DesignService service;

    @Autowired
    public DesignTacoController(DesignService service) {
        this.service = service;
    }

    @ModelAttribute
    public void addIngredientToModel(Model model) {
        service.addIngredientsToModel(model);
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @Transactional
    @PostMapping
    public String processTaco(@Valid Taco taco,
                              Errors errors,
                              @ModelAttribute TacoOrder tacoOrder) {
        if (!service.processTaco(taco, errors, tacoOrder)) {
            return "design";
        }

        return "redirect:/orders";
    }
}
