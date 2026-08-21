package it.unitn.disi.webapp_team04.controllers;

import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDashboardController {
    UserRepository userRepository;

    public UserDashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profilo")
    public String profilo(Authentication authentication, Model model){
        User u = userRepository.getUser(authentication.getName());
        model.addAttribute("nome", u.getNome());
        model.addAttribute("cognome", u.getCognome());
        model.addAttribute("data_nascita", u.getDataDisplay());
        model.addAttribute("ruolo", u.getRuoloDisplay());
        return "private/user/profilo";
    }
}
