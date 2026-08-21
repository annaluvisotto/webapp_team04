package it.unitn.disi.webapp_team04.controllers;

import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

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
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority()); //per la navbar della view
        model.addAttribute("activePage", "profilo");
        return "private/user/profilo";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("activePage", "logout");
        return "public/logout_confirmation";
    }

    @GetMapping("/upgrade_prova")
    public String upgrade_prova(Model model){
        model.addAttribute("activePage", "upgrade");
        return "private/user/upgrade_prova";
    }

    @GetMapping("/upgrade_basic")
    public String upgrade_basic(Model model){
        model.addAttribute("activePage", "upgrade");
        return "private/user/upgrade_basic";
    }

    @PostMapping("/gestione_upgrade")
    public String gestione_upgrade(@RequestParam String ruolo, Authentication authentication, Model model){
        userRepository.updateUser(authentication.getName(), ruolo);
        //creo un nuovo oggetto authentication, altrimenti quello vecchio non viene aggiornato fino al termine della sessione (e il ruolo con cambia)
        SimpleGrantedAuthority nuovaAuthority = new SimpleGrantedAuthority(ruolo);
        Authentication nuovaAutenticazione = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                Collections.singletonList(nuovaAuthority)
        );
        SecurityContextHolder.getContext().setAuthentication(nuovaAutenticazione);
        model.addAttribute("activePage", "upgrade");
        return "private/user/upgrade_confirmation";
    }
}
