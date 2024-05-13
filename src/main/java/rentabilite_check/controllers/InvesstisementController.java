package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rentabilite_check.entities.Investissement;
import rentabilite_check.entities.Projet;
import rentabilite_check.repositories.InvestissementRepository;
import rentabilite_check.repositories.ProjetRepository;

import java.util.List;

@Controller
public class InvesstisementController {

    @Autowired
    InvestissementRepository investissementRepository;
    @Autowired
    ProjetRepository projetRepository;

    @GetMapping("/investissement")
    public String investissement(Model model) {
        model.addAttribute("investissements", investissementRepository.findAll());
        model.addAttribute("projets", projetRepository.findAll());
        return "user/investissement";
    }

    @PostMapping("ajouter-investissement")
    public String ajouterInvestissement(@RequestBody Investissement investissement) {
        investissementRepository.save(investissement);
        return "user/investissement";
    }

    @GetMapping("supprimer-investissement")
    public String supprimerInvestissement(@RequestParam int id) {
        investissementRepository.deleteById(id);
        return "redirect:/investissement";
    }

    @PostMapping("get-investissement")
    public @ResponseBody Investissement getInvestissement(@RequestParam int id) {
        return investissementRepository.findInvestissementById(id);
    }

    @PostMapping("update-investissement")
    public String updateInvestissement(@RequestBody Investissement investissement) {
        System.out.println(investissement);
        investissementRepository.updateInvestissement(investissement.getId(), investissement.getMontantINV(), investissement.getDureeINV(), investissement.getTauxActualisation(), investissement.getIntitule(), investissement.getProjet());
        return "user/investissement";
    }

    @GetMapping("get-all-investissements")
    public @ResponseBody List<Investissement> getAllInvestissements() {
        return investissementRepository.findAll();
    }

    @GetMapping("find-investissement-by-projet")
    public @ResponseBody List<Investissement> findInvestissementByProjet(@RequestParam int idProjet) {
        Projet projet = projetRepository.findByIdProjet(idProjet);
        System.out.println(projet.getNom());
        return investissementRepository.findInvestissementByProjet(projet);
    }
}
