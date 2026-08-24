package it.unitn.disi.webapp_team04.controllers;

import it.unitn.disi.webapp_team04.pojos.TrainingStats;
import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.repositories.TrainingRepository;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import it.unitn.disi.webapp_team04.services.CheckUser;
import it.unitn.disi.webapp_team04.services.TrainingRest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserDashboardController {
    UserRepository userRepository;
    TrainingRepository trainingRepository;
    CheckUser checkUser;
    TrainingRest trainingRest;

    public UserDashboardController(UserRepository userRepository, TrainingRepository trainingRepository, CheckUser checkUser, TrainingRest trainingRest) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.checkUser = checkUser;
        this.trainingRest = trainingRest;
    }

    @GetMapping("/profilo")
    public String profilo(Authentication authentication, Model model){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
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
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "upgrade");
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
        return "private/user/upgrade_prova";
    }

    @GetMapping("/upgrade_basic")
    public String upgrade_basic(Authentication authentication, Model model){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "upgrade");
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
        return "private/user/upgrade_basic";
    }

    @PostMapping("/gestione_upgrade")
    public String gestione_upgrade(@RequestParam String ruolo, Authentication authentication, Model model){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
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
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("activePage", "cambio_pw");
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
        return "private/user/cambio_pw";
    }

    @PostMapping("/gestione_cambiopw")
    public String gestione_cambiopw(@RequestParam String oldPassword, @RequestParam String newPassword, Authentication authentication, Model model){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
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

    @GetMapping("/statistiche_user")
    public String UserStats(Authentication authentication, Model model){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
        model.addAttribute("nome", username);
        model.addAttribute("activePage", "statistiche_user");
        List<TrainingStats> defaultStats = trainingRepository.getDefaultTrainingsStats(username);
        List<TrainingStats> personalizedStats = trainingRepository.getPersonalizedTrainingsStats(username);
        List<TrainingStats> trainings = trainingRest.getAllTrainings();

        List<String> nomi = new ArrayList<>();
        List<Integer> esecuzioni = new ArrayList<>();

        if (trainings != null) {
            for (TrainingStats training : trainings) {
                int idAllenamento = training.getId();
                String nomeAllenamento = training.getNome();
                int exec = 0;
                for (TrainingStats stat : defaultStats) {
                    if (stat.getId() == idAllenamento) {
                        exec = stat.getExec();
                        break;
                    }
                }

                nomi.add(nomeAllenamento);
                esecuzioni.add(exec);
            }
        }

        for (TrainingStats stat : personalizedStats) {
            String nomeAllenamento = stat.getNome();
            int exec = stat.getExec();

            nomi.add(nomeAllenamento);
            esecuzioni.add(exec);
        }

        model.addAttribute("nomi", nomi);
        model.addAttribute("exec", esecuzioni);

        return "private/user/statistiche_user";
    }

}
