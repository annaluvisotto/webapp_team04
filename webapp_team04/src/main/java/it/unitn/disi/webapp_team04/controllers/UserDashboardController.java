package it.unitn.disi.webapp_team04.controllers;

import it.unitn.disi.webapp_team04.pojos.*;
import it.unitn.disi.webapp_team04.repositories.RecensioneRepository;
import it.unitn.disi.webapp_team04.repositories.TrainingRepository;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import it.unitn.disi.webapp_team04.services.CheckUser;
import it.unitn.disi.webapp_team04.services.Recensioni;
import it.unitn.disi.webapp_team04.services.TrainingRest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class UserDashboardController {
    UserRepository userRepository;
    TrainingRepository trainingRepository;
    RecensioneRepository recensioneRepository;
    CheckUser checkUser;
    TrainingRest trainingRest;
    Recensioni recensioni; //service

    @Autowired
    public UserDashboardController(UserRepository userRepository, TrainingRepository trainingRepository, CheckUser checkUser, TrainingRest trainingRest, Recensioni recensioni, RecensioneRepository recensioneRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.checkUser = checkUser;
        this.trainingRest = trainingRest;
        this.recensioni = recensioni;
        this.recensioneRepository = recensioneRepository;
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
    public String gestione_upgrade(@RequestParam String ruolo, Authentication authentication){
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
        return "redirect:/dashboard?success=upgrade"; //per l'alert
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
            model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
            return "redirect:/dashboard?success=password"; //per l'alert
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

    @PostMapping("/inserimento_recensione")
    @ResponseBody
    public Recensione inserisci_recensione(@RequestBody Recensione dati, Authentication authentication){
        Recensione r = recensioni.addRecensione(dati.getTitolo(), dati.getTesto(), authentication.getName());
        return r;
    }

    @GetMapping("/carosello_recensioni")
    @ResponseBody
    public List<Recensione> carosello_recensioni(){
        return recensioneRepository.getRecensioni();
    }

    @GetMapping("/allenamento")
    public String allenamento(Authentication authentication, Model model){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority());
        model.addAttribute("nome", username);
        model.addAttribute("activePage", "allenamento");
        List<Training> defaultTrainings = trainingRest.getAllTrainingsComposition();
        model.addAttribute("defaultTrainings", defaultTrainings);

        if ("ROLE_USER_PRO".equals(model.getAttribute("authority"))) {
            List<Training> customTrainings = trainingRepository.getPersonalizedTrainings(username);
            for (Training custom : customTrainings) {
                if (custom.getEsercizi() != null && !custom.getEsercizi().isEmpty()) {
                    int kcal = trainingRest.getKcal(custom.getEsercizi());
                    custom.setKcal(kcal);
                }
            }
            model.addAttribute("personalizedTrainings", customTrainings);
        }

        return "private/user/allenamento";
    }

    @PostMapping("/training/completed")
    @ResponseBody
    public Map<String, Object> training_completed (@RequestParam(value = "id", required = false) Integer id, @RequestParam("tipo") String tipo, Authentication authentication, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("redirect", "/login");
            return response;
        }

        if (id == null) {
            response.put("alert", "Errore: ID dell'allenamento non valido.");
            response.put("redirect", "/allenamento");
            return response;
        }

        String username = authentication.getName();
        String authority = authentication.getAuthorities().iterator().next().getAuthority();
        int totaleEsecuzioni = trainingRepository.getTotalExecutions(username);

        try {
            if ("DEFAULT".equals(tipo)) {
                if ("ROLE_USER_PROVA".equals(authority) && totaleEsecuzioni>=2) {
                    userRepository.disableUser(username);
                    request.getSession().invalidate();
                    SecurityContextHolder.clearContext();
                    response.put("alert", "Hai completato 3/3 allenamenti. Questo account non è più valido.");
                    response.put("redirect", "/index");
                    return response;
                }
                else {
                    trainingRepository.incrementDefaultExec(username, id);
                    response.put("alert", "Allenamento di default completato con successo!");
                }
            }
            else if ("PERSONALIZED".equals(tipo)){
                trainingRepository.incrementPersonalizedExec(username, id);
                response.put("alert", "Allenamento personalizzato completato con successo!");
            }
        } catch (Exception e) {
            System.err.println("Errore salvataggio esecuzione: " + e.getMessage());
            response.put("alert", "Si è verificato un errore durante il salvataggio.");
            response.put("redirect", "/allenamento");
        }

        response.put("redirect", "/dashboard");
        return response;
    }

    @GetMapping("/inserisci_programma")
    public String inserisci_programma(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String authority = authentication.getAuthorities().iterator().next().getAuthority();
        if (!"ROLE_USER_PRO".equals(authority)) {
            return "redirect:/dashboard";
        }

        List<Exercise> catalogoEsercizi = trainingRest.getAllExercises();
        model.addAttribute("catalogoEsercizi", catalogoEsercizi);
        model.addAttribute("authority", authority);
        model.addAttribute("activePage", "inserisci_programma");
        return "private/user/inserisci_programma";
    }

    @PostMapping("/addPersTraining")
    @ResponseBody
    public Map<String, Object> salvaAllenamento(@ModelAttribute Training training, Authentication authentication, Model model) {
        Map<String, Object> response = new HashMap<>();

        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("redirect", "/login");
            return response;
        }

        String username = authentication.getName();
        String authority = authentication.getAuthorities().iterator().next().getAuthority();

        if (!trainingRepository.uniquePersonalizedTraining(username, training.getNome())) {
            response.put("error", "Hai già un allenamento con questo nome.");
            return response;
        }

        if (training.getEsercizi() != null && !training.getEsercizi().isEmpty()) {
            trainingRepository.savePersonalizedTraining(username, training);
        }

        int totKcal = trainingRest.getKcal(training.getEsercizi());
        response.put("alert", "Allenamento salvato con successo!\nConsumo totale: " + totKcal + " kcal");
        response.put("redirect", "/dashboard");

        return response;
    }

    @GetMapping("/contatti_user")
    public String contatti(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("authority", authentication.getAuthorities().iterator().next().getAuthority()); //per la navbar della view
        model.addAttribute("activePage", "contatti_user");
        return "private/user/contatti_user";
    }
}
