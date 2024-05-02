package rentabilite_check.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String home() {
        return "user/dashboard";
    }
}
