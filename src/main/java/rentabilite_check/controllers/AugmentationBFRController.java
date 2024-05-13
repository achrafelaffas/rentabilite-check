package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rentabilite_check.entities.AugmentationBFR;
import rentabilite_check.entities.CustomUserDetails;
import rentabilite_check.entities.Projet;
import rentabilite_check.repositories.AugmentationBFRRepository;
import rentabilite_check.repositories.ProjetRepository;
import rentabilite_check.repositories.UserRepository;

import java.util.List;

@Controller
public class AugmentationBFRController {

    @Autowired
    private AugmentationBFRRepository augmentationBFRRepository;
    @Autowired
    private ProjetRepository projetRepository;

    @GetMapping("/augmentation-bfr")
    public String augmentationBfr(Model model) {
        model.addAttribute("augbfrlist", augmentationBFRRepository.findAll());
        model.addAttribute("projets", projetRepository.findAll());
        return "/user/augmentationBFR";
    }

    @PostMapping("/ajouter-augmentation-bfr")
    public String ajouterAugmentationBfr(@RequestBody AugmentationBFR augmentationBFR) {
        augmentationBFRRepository.save(augmentationBFR);
        return "user/augmentationBFR";
    }

    @GetMapping("/supprimer-aug")
    public String supprimerProjet(@RequestParam int idaUGbFR) {
        AugmentationBFR augmentationBFR = augmentationBFRRepository.findAugmentationBFRByIdaUGbFR(idaUGbFR);
        augmentationBFRRepository.delete(augmentationBFR);
        return "redirect:/augmentation-bfr";
    }

    @GetMapping("get-augbfr-a-modifier")
    public @ResponseBody AugmentationBFR getAugmentationBFRModifier(@RequestParam int idaUGbFR) {
        return augmentationBFRRepository.findAugmentationBFRByIdaUGbFR(idaUGbFR);
    }

    @PostMapping("update-bfr")
    public String updateBfr(@RequestBody AugmentationBFR augmentationBFR) {
        System.out.println(augmentationBFR.getNom());
        augmentationBFRRepository.updateAugBFR(
                augmentationBFR.getIdaUGbFR(),
                augmentationBFR.getNom(),
                augmentationBFR.getAnnee(),
                augmentationBFR.getMontant(),
                augmentationBFR.getProjet()
        );
        return "user/augmentationBFR";
    }

    @GetMapping("get-bfrs")
    public @ResponseBody List<AugmentationBFR> getAugmentationBFRs() {
        return augmentationBFRRepository.findAll();
    }

    @GetMapping("get-bfrs-by-projet")
    public @ResponseBody List<AugmentationBFR> getBfrsByProjet(@RequestParam int idProjet) {
        Projet p = projetRepository.findByIdProjet(idProjet);
        return augmentationBFRRepository.findAugmentationBFRByProjet(p);
    }
}
