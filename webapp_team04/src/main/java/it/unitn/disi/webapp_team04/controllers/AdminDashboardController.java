package it.unitn.disi.webapp_team04.controllers;


import it.unitn.disi.webapp_team04.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {
    UserRepository userRepository;

    public AdminDashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/lista_utenti")
    public String lista_utenti(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "dashboard");
        return "private/admin/lista_utenti";
    }

    @GetMapping("/rimuovi_utenti")
    public String rimuovi_utenti(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "dashboard");
        return "private/admin/rimuovi_utenti";
    }

    @GetMapping("/statistiche_admin")
    public String statistiche_admin(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "dashboard");
        return "private/admin/statistiche_admin";
    }

}
