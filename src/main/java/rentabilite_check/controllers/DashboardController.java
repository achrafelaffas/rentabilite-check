package rentabilite_check.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rentabilite_check.entities.Investissement;
import rentabilite_check.repositories.InvestissementRepository;
import rentabilite_check.repositories.ProjetRepository;

import java.util.List;

@Controller
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
    InvestissementRepository investissementRepository;

    @Autowired
    ProjetRepository projetRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("projets", projetRepository.count());
        model.addAttribute("investissements", investissementRepository.count());
        return "user/dashboard";
    }

    @GetMapping("/get-project-investment-sum")
    public @ResponseBody List<Object[]> getProjectInvestmentSum() {
        return investissementRepository.getProjectInvestmentSum();
    }
}
