package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rentabilite_check.entities.AugmentationBFR;
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
        return "user/projets";
    }
}
