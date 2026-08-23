package it.unitn.disi.webapp_team04.controllers;

import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import it.unitn.disi.webapp_team04.services.CheckUser;
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
    CheckUser checkUser;

    public UserDashboardController(UserRepository userRepository, CheckUser checkUser) {
        this.userRepository = userRepository;
        this.checkUser = checkUser;
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

    @GetMapping("/upgrade_prova")
    public String upgrade_prova(Authentication authentication, Model model){
        model.addAttribute("activePage", "upgrade");
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
        return "private/user/upgrade_prova";
    }

    @GetMapping("/upgrade_basic")
    public String upgrade_basic(Authentication authentication, Model model){
        model.addAttribute("activePage", "upgrade");
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
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

    @GetMapping("/cambio_pw")
    public String cambio_pw(Authentication authentication, Model model){
        model.addAttribute("activePage", "cambio_pw");
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
        return "private/user/cambio_pw";
    }

    @PostMapping("/gestione_cambiopw")
    public String gestione_cambiopw(@RequestParam String oldPassword, @RequestParam String newPassword, Authentication authentication, Model model){
        if(!checkUser.updatePassword(authentication.getName(), oldPassword, newPassword)){
            model.addAttribute("errore", "#team_04: The old password is incorrect");
            model.addAttribute("activePage", "cambio_pw");
            model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
            return "private/user/cambio_pw";
        }
        else{
            model.addAttribute("activePage", "cambio_pw");
            model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
            return "private/user/cambiopw_confirmation";
        }
    }
}
