package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rentabilite_check.entities.ChargesVariables;
import rentabilite_check.entities.Projet;
import rentabilite_check.repositories.ChargesVariablesRepository;
import rentabilite_check.repositories.ProjetRepository;

import java.util.List;

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

    @GetMapping("/cvariable")
    public @ResponseBody List<ChargesVariables> getChargesVariablesByProjet(@RequestParam int idProjet) {
        Projet projet = projetRepository.findById(idProjet).orElse(null);
        if (projet != null) {
            List<ChargesVariables> chargesVariables = chargevariablerepo.findByProjet(projet);
            return chargesVariables;
        }
        return null;
    }

    @PostMapping("/ajouter-CHV")
    public String ajouterChargeVariable(@RequestBody ChargesVariables chargesVariables, @RequestParam int projetId) {
        Projet projet = projetRepository.findByIdProjet(projetId);
        chargesVariables.setProjet(projet);
        chargevariablerepo.save(chargesVariables);
        return "redirect:/cv";
    }

    @GetMapping("/deleteCV")
    public String supprimerCV(@RequestParam int idCV) {
        System.out.println(idCV);
        ChargesVariables chargesVariables = chargevariablerepo.findByIdCV(idCV);
        chargevariablerepo.delete(chargesVariables);
        return "redirect:/cv";
    }

    @GetMapping("/get-chargevariable-a-modifier")
    public @ResponseBody
    ChargesVariables getCVAModifier(@RequestParam int idCV) {
        return chargevariablerepo.findByIdCV(idCV);
    }

    @PostMapping("/update-CV")
    public String updateCV(@RequestBody ChargesVariables chargesVariables) {
        chargevariablerepo.updateChargeVariable(
                chargesVariables.getIdCV(),
                chargesVariables.getNom(),
                chargesVariables.getAnnee(),
                chargesVariables.getMontant(),
                chargesVariables.getProjet()
        );
        return "redirect:/cv";
    }

    @GetMapping("/get-all-chargevariables")
    public @ResponseBody
    List<ChargesVariables> getListCV() {
        return chargevariablerepo.findAll();
    }
}
