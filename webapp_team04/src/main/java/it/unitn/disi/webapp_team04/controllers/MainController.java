package it.unitn.disi.webapp_team04.controllers;

import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.repositories.CheckUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final CheckUser checkUser;

    @Autowired
    private MainController(CheckUser checkUser) {
        this.checkUser = checkUser;
    }

    @GetMapping
    public String index(){
        return "public/index";
    }

    @GetMapping("/signup")
    public String signup(){
        return "public/signup";
    }

    @GetMapping("/mylogin")
    public String login(){
        return "public/login";
    }

    @PostMapping("/login_failure")
    public String login_failure(Model model){
        model.addAttribute("errore", "#team_04: That user is not authenticated!");
        return "public/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "private/admin/dashboard_admin";
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
        if(checkUser.checkUsername(username)){
            //uso il model per passare l'errore alla view
            model.addAttribute("errore", "#team_04: This username is already taken, provide another one");
            return "public/signup";
        }
        else{
            User user = new User();
            user.setNome(nome);
            user.setCognome(cognome);
            user.setData_nascita(data);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setRuolo(ruolo);
            checkUser.addUser(user);
            return "public/reg_confirmation";
        }

    }




}
