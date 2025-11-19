package org.example.clickearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/test") // nouvelle page de test, PAS "/"
    public String testPage() {
        return "forward:/test.html"; // fichier test.html dans static
    }

    @GetMapping("/api/test")
    @ResponseBody
    public String apiTest() {
        return "✅ API TEST - " + java.time.LocalDateTime.now();
    }

    @GetMapping("/api/check-files")
    @ResponseBody
    public String checkFiles() {
        return "Fichiers statiques détectés - Spring Boot configuré correctement";
    }
}
