package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rentabilite_check.entities.ChargesFixes;
import rentabilite_check.entities.ChiffreAffaire;
import rentabilite_check.entities.Projet;
import rentabilite_check.repositories.ChiffreAffaireRepository;
import rentabilite_check.repositories.ProjetRepository;

import java.util.List;

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


    //Pour selectione le projet
    @GetMapping("/chiffresaffaires")
    public @ResponseBody List<ChiffreAffaire> getChiffreAffairesByProjet(@RequestParam int idProjet) {
        Projet projet = projetRepository.findById(idProjet).orElse(null);
        if (projet != null) {
            List<ChiffreAffaire> ChiffreAffaire = chiffreaffairerepo.findByProjet(projet);
            return ChiffreAffaire;
        }
        return null;
    }

    //Ajouter charge fixe
    @PostMapping("/ajouter-CHIAFF")
    public String ajouterChiffreAffaire(@RequestBody ChiffreAffaire chiffreaffaire, @RequestParam int projetId) {
        Projet projet = projetRepository.findByIdProjet(projetId);
        chiffreaffaire.setProjet(projet);
        chiffreaffairerepo.save(chiffreaffaire);
        return "redirect:/ca";
    }

    //Supprimer
    @GetMapping("/deleteCHIAFF")
    public String supprimerCHIAFF(@RequestParam int idChiffreAffaire) {
        ChiffreAffaire chargesaFF=chiffreaffairerepo.findByIdChiffreAffaire(idChiffreAffaire);
        chiffreaffairerepo.delete(chargesaFF);
        return "redirect:/ca";
    }

    //Pour recuperer les infos de CF a modifier
    @GetMapping("get-chiffreAFF-a-modifier")
    public @ResponseBody ChiffreAffaire getCHIAFFAModifier(@RequestParam int idChiffreAffaire) {
        return chiffreaffairerepo.findByIdChiffreAffaire(idChiffreAffaire);
    }

    @PostMapping("update-CHAFF")
    public String updateCF(@RequestBody ChiffreAffaire chiffreaffaire) {

        chiffreaffairerepo.updateChiffreAffaire(
                chiffreaffaire.getIdChiffreAffaire(),
                chiffreaffaire.getPrixUnitaire(),
                chiffreaffaire.getQuantite(),
                chiffreaffaire.getValeur(),
                chiffreaffaire.getAnnee(),
                chiffreaffaire.getProjet()

        );


        return "redirect:/ca";
    }
    @GetMapping("get-all-chiffreaffaires")
    public @ResponseBody List<ChiffreAffaire> getListCHIAFF() {
        return chiffreaffairerepo.findAll();
    }
}
