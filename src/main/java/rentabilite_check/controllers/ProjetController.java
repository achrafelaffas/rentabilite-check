package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rentabilite_check.entities.CustomUserDetails;
import rentabilite_check.entities.CustomUserDetailsService;
import rentabilite_check.entities.Projet;
import rentabilite_check.entities.User;
import rentabilite_check.repositories.ProjetRepository;
import rentabilite_check.repositories.UserRepository;

import java.util.List;

@Controller
public class ProjetController {

    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/projets")
    public String projet(Model model) {
        model.addAttribute("projets", projetRepository.findAll());
        return "user/projets";
    }

    @GetMapping("/get-projets")
    public @ResponseBody List<Projet> getProjets() {
        return projetRepository.findAll();
    }

    @PostMapping("/ajouter-projet")
    public String ajouterProjet(@RequestBody Projet projet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByEmail(auth.getName());
        if (user != null) {
            projet.setUser(user);
            projetRepository.save(projet);
        }
        return "user/projets";
    }

    @GetMapping("/supprimer-projet")
    public String supprimerProjet(@RequestParam int idProjet) {
        Projet projet = projetRepository.findByIdProjet(idProjet);
        projetRepository.delete(projet);
        return "redirect:/projets";
    }

    @GetMapping("get-projet-a-modifier")
    public @ResponseBody Projet getProjetAModifier(@RequestParam int idProjet) {
        return projetRepository.findByIdProjet(idProjet);
    }

    @PostMapping("/update-projet")
    public String updateProjet(@RequestBody Projet p) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByEmail(auth.getName());
        if (user != null) {
            p.setUser(user);
        }

        projetRepository.updateProjet(
                p.getIdProjet(),
                p.getNom(),
                p.getDescription(),
                p.getTauxActualisation(),
                p.getImpotSociete(),
                p.getDuree()
        );

        return "user/projets";
    }
}
