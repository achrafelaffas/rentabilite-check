package rentabilite_check.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjetController {

    @GetMapping("/projets")
    public String projet() {
        return "user/projets";
    }
}
