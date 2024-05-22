package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import rentabilite_check.services.ProjetService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjetController {

    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjetService projetService;

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
    public @ResponseBody String ajouterProjet(@RequestBody Projet projet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByEmail(auth.getName());
        if (user != null) {
            projet.setUser(user);
            projetRepository.save(projet);
        }
        return "inserted";
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
                p.getImpotSociete(),
                p.getDuree(),
                p.getCaptial()
        );

        return "user/projets";
    }


    @GetMapping("/projet/{id}/cashflows")
    public ResponseEntity<List<Double>> calculateCashFlows(@PathVariable("id") int id) {
        List<Double> cashFlows = projetService.calculateCashFlows(id);
        if (cashFlows == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cashFlows);
    }


    @PostMapping("/insertVANIPDRCI")
    public ResponseEntity<String> insertVANIPDRCI(@RequestParam int idProjet,
                                                  @RequestParam double van,
                                                  @RequestParam double ip,
                                                  @RequestParam int drci) {
        try {
            projetRepository.updateProjetCalculations(idProjet, van, ip, drci);
            return ResponseEntity.ok("Calculations inserted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to insert calculations: " + e.getMessage());
        }
    }




}
