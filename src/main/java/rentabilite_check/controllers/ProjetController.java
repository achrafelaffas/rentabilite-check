package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rentabilite_check.entities.CustomUserDetails;
import rentabilite_check.entities.Projet;
import rentabilite_check.entities.User;
import rentabilite_check.repositories.ProjetRepository;

@Controller
public class ProjetController {

    @Autowired
    private ProjetRepository projetRepository;

    @GetMapping("/projets")
    public String projet(Model model) {
        model.addAttribute("projets", projetRepository.findAll());
        for (Projet projet : projetRepository.findAll()) {
            System.out.println(projet.toString());
        }
        return "user/projets";
    }

    @PostMapping("/ajouter-projet")
    public String ajouterProjet(@RequestBody Projet projet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        projet.setUser(userDetails.getUser());
        projetRepository.save(projet);
        return "user/projets";
    }
}
