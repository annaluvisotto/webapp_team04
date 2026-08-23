package it.unitn.disi.webapp_team04.controllers;

import it.unitn.disi.webapp_team04.repositories.TrainingRepository;
import it.unitn.disi.webapp_team04.services.CheckUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final CheckUser checkUser;
    private final TrainingRepository trainingRepository;

    @Autowired
    private MainController(CheckUser checkUser, TrainingRepository trainingRepository) {
        this.checkUser = checkUser;
        this.trainingRepository = trainingRepository;
    }

    @GetMapping("/index")
    public String index(){
        return "public/index";
    }

    @GetMapping("/signup")
    public String signup(){
        return "public/signup";
    }

    @PostMapping("/adduser")
    //crea un oggetto User riempiendo i campi con i valori inseriti dall'utente
    public String adduser(@RequestParam String nome,
                          @RequestParam String cognome,
                          @RequestParam String data,
                          @RequestParam String email,
                          @RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String ruolo,
                          Model model){
        if(!checkUser.addUser(nome, cognome, data, email, username, password, ruolo)){
            model.addAttribute("errore", "#team_04: This username is already taken, please enter another one");
            return "public/signup";
        }
        else{
            return "public/reg_confirmation";
        }
    }

    @GetMapping({"/mylogin", "/login"})
    public String login(){
        return "public/login";
    }

    @PostMapping("/login_failure")
    public String login_failure(Model model){
        model.addAttribute("errore", "#team_04: That user is not authenticated!");
        return "public/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        String view;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            view = "forward:dashboard_admin";
        else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER_PROVA")))
            view = "forward:dashboard_prova";
        else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER_BASIC")))
            view = "forward:dashboard_basic";
        else
            view = "forward:dashboard_pro";
        return view;
    }

    @GetMapping("/dashboard_admin")
    public String dashboard_admin(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "dashboard");
        return "private/admin/dashboard_admin";
    }

    @GetMapping("/dashboard_prova")
    public String dashboard_prova(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("esecuzioni", trainingRepository.getExec(authentication.getName()));
        return "private/user/dashboard_prova";
    }

    @GetMapping("/dashboard_basic")
    public String dashboard_basic(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "dashboard");
        return "private/user/dashboard_basic";
    }

    @GetMapping("/dashboard_pro")
    public String dashboard_pro(Authentication authentication, Model model) {
        model.addAttribute("nome", authentication.getName());
        model.addAttribute("activePage", "dashboard");
        return "private/user/dashboard_pro";
    }

    @GetMapping("/contatti")
    public String contatti() {
        return "public/contatti";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("activePage", "logout");
        return "public/logout_confirmation";
    }

}
