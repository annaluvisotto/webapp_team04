package it.unitn.disi.webapp_team04.controllers;

import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegController {
    private final UserRepository userRepository;
    private RegController(UserRepository utenteRepository) {
        this.userRepository = utenteRepository;
    }

    @PostMapping("/adduser")
    //crea un oggetto User riempiendo i campi con i valori inseriti dall'utente
    public String adduser(@RequestParam String nome, @RequestParam String cognome, @RequestParam String data, @RequestParam String email, @RequestParam String username, @RequestParam String psw1, @RequestParam String pianoScelto){
        User user = new User();
        user.setNome(nome);
        user.setCognome(cognome);
        user.setData_nascita(data);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(psw1);
        user.setAuthority(pianoScelto);
        userRepository.addUser(user);
        return "public/reg_confirmation";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        return "public/signup";
    }


}
