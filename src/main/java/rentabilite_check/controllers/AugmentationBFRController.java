package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rentabilite_check.entities.AugmentationBFR;
import rentabilite_check.entities.Projet;
import rentabilite_check.repositories.AugmentationBFRRepository;

@Controller
public class AugmentationBFRController {

    @Autowired
    private AugmentationBFRRepository augmentationBFRRepository;

    @GetMapping("/augmentation-bfr")
    public String augmentationBfr(Model model) {
        model.addAttribute("augbfrlist", augmentationBFRRepository.findAll());
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
}
