package it.unitn.disi.webapp_team04.services;

import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class CheckUser {
    private final UserRepository userRepository;

    public CheckUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean adduser(String nome, String cognome, String data_nascita, String email, String username, String password, String ruolo) {
        if (userRepository.checkUsername(username)) {
            return false;
        } else {
            String data_reg = LocalDate.now().toString();
            User user = new User(nome, ruolo, password, username, email, data_nascita, cognome, data_reg);
            userRepository.addUser(user);
            return true;
        }
    }
}
