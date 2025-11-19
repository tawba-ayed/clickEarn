package org.example.clickearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // accueil principal
    public String home() {
        return "forward:/index.html"; // cherche index.html dans static
    }
}
