package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rentabilite_check.repositories.ChiffreAffaireRepository;
import rentabilite_check.repositories.ProjetRepository;

@Controller
public class ChiffreAffaireController {

    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private ChiffreAffaireRepository chiffreaffairerepo;

    @GetMapping("/ca")
    public String pageChiffreAffaire(Model model) {
        model.addAttribute("chiffreaffaire",chiffreaffairerepo.findAll());
        model.addAttribute("listeprojets",projetRepository.findAll());
        return "user/chiffreaffaire";
    }
}
