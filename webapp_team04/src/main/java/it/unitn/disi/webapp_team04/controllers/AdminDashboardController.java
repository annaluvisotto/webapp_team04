package it.unitn.disi.webapp_team04.controllers;


import it.unitn.disi.webapp_team04.pojos.TrainingStats;
import it.unitn.disi.webapp_team04.repositories.TrainingRepository;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import it.unitn.disi.webapp_team04.services.TrainingRest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminDashboardController {
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingRest trainingRest;

    public AdminDashboardController(UserRepository userRepository, TrainingRepository trainingRepository, TrainingRest trainingRest) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.trainingRest = trainingRest;
    }

    @GetMapping("/lista_utenti")
    public String lista_utenti(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "lista_utenti");
        model.addAttribute("utenti", userRepository.getAllUsers());
        return "private/admin/lista_utenti";
    }

    @GetMapping("/rimuovi_utenti")
    public String rimuovi_utenti(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "rimuovi_utenti");
        return "private/admin/rimuovi_utenti";
    }

    @GetMapping("/statistiche_admin")
    public String statistiche_admin(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "statistiche_admin");
        List<List<TrainingStats>> adminStats = trainingRepository.gatAdminStats();
        List<TrainingStats> trainings = trainingRest.getAllTrainings();

        List<String> nomi = new ArrayList<>();
        List<Integer> basic_stats = new ArrayList<>();
        List<Integer> pro_stats = new ArrayList<>();
        for (List<TrainingStats> coppia : adminStats){
            TrainingStats basicStat = coppia.get(0);
            TrainingStats proStat = coppia.get(1);
            int idCercato = basicStat.getId();

            String nomeTrovato = "Allenamento: " + idCercato;
            for (TrainingStats training : trainings) {
                if (training.getId() == idCercato) {
                    nomeTrovato = training.getNome();
                    break;
                }
            }

            basicStat.setNome(nomeTrovato);
            proStat.setNome(nomeTrovato);
            nomi.add(nomeTrovato);
            basic_stats.add(basicStat.getExec());
            pro_stats.add(proStat.getExec());
        }

        model.addAttribute("nomi", nomi);
        model.addAttribute("datiBasic", basic_stats);
        model.addAttribute("datiPro", pro_stats);

        return "private/admin/statistiche_admin";
    }

}
