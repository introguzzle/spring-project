package ru.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.delivery.entity.Text;

@Controller
@RequestMapping("/input")
@SessionAttributes("output")
public class InputController {

    @GetMapping
    public String view() {
        return "input";
    }

    @ModelAttribute(name = "string")
    public Text string() {
        return new Text();
    }

    @PostMapping
    public String post(@ModelAttribute Text string, Model model) {
        model.addAttribute("output", string.getValue());

        return "input";
    }
}
