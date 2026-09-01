package it.unitn.disi.webapp_team04.controllers;


import it.unitn.disi.webapp_team04.pojos.TrainingStats;
import it.unitn.disi.webapp_team04.repositories.TrainingRepository;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import it.unitn.disi.webapp_team04.services.TrainingRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminDashboardController {
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingRest trainingRest;

    @Autowired
    public AdminDashboardController(UserRepository userRepository, TrainingRepository trainingRepository, TrainingRest trainingRest) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.trainingRest = trainingRest;
    }

    @GetMapping("/lista_utenti")
    public String lista_utenti(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "lista_utenti");
        model.addAttribute("utenti", userRepository.getAllUsers());
        return "private/admin/lista_utenti";
    }

    @GetMapping("/rimuovi_utenti")
    public String rimuovi_utenti(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "rimuovi_utenti");
        return "private/admin/rimuovi_utenti";
    }

    @PostMapping("/elimina_disabilitati")
    @ResponseBody
    public String elimina_disabilitati(){
        Integer num_users = userRepository.removeDisabledUsers();
        return String.valueOf(num_users);
    }

    @GetMapping("/statistiche_admin")
    public String statistiche_admin(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "statistiche_admin");
        List<List<TrainingStats>> adminStats = trainingRepository.getAdminStats();
        List<TrainingStats> trainings = trainingRest.getAllTrainings();

        List<String> nomi = new ArrayList<>();
        List<Integer> basic_stats = new ArrayList<>();
        List<Integer> pro_stats = new ArrayList<>();

        if (trainings != null) {
            for (TrainingStats training : trainings) {
                int idAllenamento = training.getId();
                String nomeAllenamento = training.getNome();
                int mediaBasic = 0;
                int mediaPro = 0;
                for (List<TrainingStats> coppia : adminStats) {
                    if (coppia.get(0).getId() == idAllenamento) {
                        mediaBasic = coppia.get(0).getExec();
                        mediaPro = coppia.get(1).getExec();
                        break;
                    }
                }

                nomi.add(nomeAllenamento);
                basic_stats.add(mediaBasic);
                pro_stats.add(mediaPro);
            }
        }

        model.addAttribute("nomi", nomi);
        model.addAttribute("datiBasic", basic_stats);
        model.addAttribute("datiPro", pro_stats);

        return "private/admin/statistiche_admin";
    }

}
