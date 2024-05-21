package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rentabilite_check.entities.Projet;
import rentabilite_check.repositories.ProjetRepository;

import java.util.List;

@Controller
public class RecommandationController {

    @Autowired
    ProjetRepository projetRepository;

    @GetMapping("/recommandations")
    public String recommandation(Model model) {
        List<Projet> projets = projetRepository.findAll();
        model.addAttribute("projets", projets);
        return "user/recommandations";
    }
    @GetMapping("/questionnaire")
    public String questionnaire(Model model) {
        return "user/questionnaire";
    }
}
