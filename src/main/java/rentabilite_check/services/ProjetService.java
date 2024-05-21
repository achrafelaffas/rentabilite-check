package rentabilite_check.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentabilite_check.entities.Projet;
import rentabilite_check.entities.ChiffreAffaire;
import rentabilite_check.repositories.ChargesFixesRepository;
import rentabilite_check.repositories.ChargesVariablesRepository;
import rentabilite_check.repositories.ChiffreAffaireRepository;
import rentabilite_check.repositories.ProjetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private ChargesFixesRepository chargesFixesRepository;

    @Autowired
    private ChargesVariablesRepository chargesVariablesRepository;

    @Autowired
    private ChiffreAffaireRepository chiffreAffaireRepository;

    public double calculateCAF(int projetId, int year) {
        Projet projet = projetRepository.findById(projetId).orElse(null);
        double chiffreAffaire = chiffreAffaireRepository.findByProjetIdAndDateYear(projetId, year).stream()
                .mapToDouble(ChiffreAffaire::getValeur)
                .sum();
        double chargesFixes = chargesFixesRepository.getTotalByYearForProjet(projetId, year);
        double chargesVariables = chargesVariablesRepository.getTotalByYearForProjet(projetId, year);

        return (chiffreAffaire - chargesFixes - chargesVariables) * (1 - projet.getImpotSociete());
    }

    public double calculateCashFlows(int projetId) {
        //Projet projet = projetRepository.findById(projetId).orElse(null);
        List<Integer> years = projetRepository.findYearsByProjetId(projetId);

        return years.stream().mapToDouble(year -> {
            //double caf = calculateCAF(projetId, year);
            //double tauxActualisation = projet.getInvestissement() != null ? projet.getInvestissement().getTauxActualisation() : 0;
            //int n = year - years.get(0) + 1; // assuming years are in order
            //return caf * Math.pow(1 + tauxActualisation, -n);
            return calculateCAF(projetId, year);
        }).sum();
    }

}
