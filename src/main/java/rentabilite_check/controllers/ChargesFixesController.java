package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/ajouter-CHfix")
    public String ajouterChargeFixe(@RequestBody ChargesFixes chargeFixe, @RequestParam int projetId) {
        Projet projet = projetRepository.findByIdProjet(projetId);
        chargeFixe.setProjet(projet);
        chargefixRepo.save(chargeFixe);
        return "redirect:/cf";
    }

    @GetMapping("/deleteCF")
    public String supprimerCF(@RequestParam int idCF) {
        ChargesFixes chargesF=chargefixRepo.findByIdCF(idCF);
        chargefixRepo.delete(chargesF);
        return "redirect:/cf";
    }


    @GetMapping("get-chargefixe-a-modifier")
    public @ResponseBody ChargesFixes getCFAModifier(@RequestParam int idCF) {
        return chargefixRepo.findByIdCF(idCF);
    }


    @GetMapping("/chargesfixes/{idProjet}")
    public String getChargesFixesByProjet(@PathVariable("idProjet") int idProjet, Model model) {
        Projet projet = projetRepository.findById(idProjet).orElse(null);
        if (projet != null) {
            List<ChargesFixes> chargesFixes = chargefixRepo.findByProjet(projet);

            model.addAttribute("listeprojets", projetRepository.findAll());
            model.addAttribute("projet", projet);
            model.addAttribute("chargesfixes", chargesFixes);
        }
        return "user/chargesfixes";
    }












    @GetMapping("get-projet")
    public @ResponseBody Projet getProjet(@RequestParam int idp) {
        return projetRepository.findByIdProjet(idp);
    }




}



