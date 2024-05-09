package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rentabilite_check.repositories.ChargesVariablesRepository;
import rentabilite_check.repositories.ProjetRepository;

@Controller
public class ChargesVariablesController {

    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private ChargesVariablesRepository chargevariablerepo;

    @GetMapping("/cv")
    public String pageChargesVariables(Model model) {

        model.addAttribute("chargesvariables",chargevariablerepo.findAll());
        model.addAttribute("listeprojets",projetRepository.findAll());

        return "user/chargesvariables";
    }
}
