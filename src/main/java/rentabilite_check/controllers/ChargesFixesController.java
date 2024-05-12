package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rentabilite_check.entities.AugmentationBFR;
import rentabilite_check.entities.ChargesFixes;
import rentabilite_check.entities.Projet;
import rentabilite_check.entities.User;
import rentabilite_check.repositories.ChargesFixesRepository;
import rentabilite_check.repositories.ProjetRepository;

import java.util.List;


@Controller
public class ChargesFixesController {
    @Autowired
    private ChargesFixesRepository chargefixRepo;
    @Autowired
    private ProjetRepository projetRepository;

    @GetMapping("/cf")
    public String pageChargesFixes(Model model) {
                model.addAttribute("chargesfixes",chargefixRepo.findAll());
                model.addAttribute("listeprojets",projetRepository.findAll());
        return "user/chargesfixes";
    }

    //Pour selectione le projet
    @GetMapping("/cfx")
    public @ResponseBody List<ChargesFixes> getChargesFixesByProjet(@RequestParam int idProjet) {
        Projet projet = projetRepository.findById(idProjet).orElse(null);
        if (projet != null) {
            List<ChargesFixes> chargesFixes = chargefixRepo.findByProjet(projet);
            return chargesFixes;
        }
        return null;
    }

    //Ajouter charge fixe
    @PostMapping("/ajouter-CHfix")
    public String ajouterChargeFixe(@RequestBody ChargesFixes chargeFixe, @RequestParam int projetId) {
        Projet projet = projetRepository.findByIdProjet(projetId);
        chargeFixe.setProjet(projet);
        chargefixRepo.save(chargeFixe);
        return "redirect:/cf";
    }

    //Supprimer
    @GetMapping("/deleteCF")
    public String supprimerCF(@RequestParam int idCF) {
        ChargesFixes chargesF=chargefixRepo.findByIdCF(idCF);
        chargefixRepo.delete(chargesF);
        return "redirect:/cf";
    }

    //Pour recuperer les infos de CF a modifier
    @GetMapping("get-chargefixe-a-modifier")
    public @ResponseBody ChargesFixes getCFAModifier(@RequestParam int idCF) {
        return chargefixRepo.findByIdCF(idCF);
    }

    @PostMapping("update-CF")
    public String updateCF(@RequestBody ChargesFixes chargefixe) {

        chargefixRepo.updateChargeFixe(
                chargefixe.getIdCF(),
                chargefixe.getNom(),
                chargefixe.getAnnee(),
                chargefixe.getMontant(),
                chargefixe.getProjet()

        );


        return "redirect:/cf";
    }
    @GetMapping("get-all-chargefixes")
    public @ResponseBody List<ChargesFixes> getListCF() {
        return chargefixRepo.findAll();
    }


}



