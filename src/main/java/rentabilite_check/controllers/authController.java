package rentabilite_check.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rentabilite_check.entities.User;
import rentabilite_check.repositories.UtilisateurRepository;

@Controller
public class authController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/auth/register";
    }

    @PostMapping("create")
    public String createUser(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encry_password = bCryptPasswordEncoder.encode(user.getMotdepasse());
        user.setMotdepasse(encry_password);
        utilisateurRepository.save(user);
        return "user/auth/login";
    }

    @GetMapping("/login")
    public String login() {
        return "user/auth/login";
    }
}
