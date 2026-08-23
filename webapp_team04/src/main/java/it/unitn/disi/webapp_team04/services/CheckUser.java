package it.unitn.disi.webapp_team04.services;

import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class CheckUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CheckUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean addUser(String nome, String cognome, String data_nascita, String email, String username, String password, String ruolo) {
        if (userRepository.checkUsername(username)) {
            return false;
        } else {
            String data_reg = LocalDate.now().toString();
            User user = new User(nome, ruolo, password, username, email, data_nascita, cognome, data_reg);
            userRepository.addUser(user);
            return true;
        }
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword){
        String dbPassword = userRepository.getPassword(username);
        if (!passwordEncoder.matches(oldPassword, dbPassword)) {
            return false;
        }
        else{
            String newPasswordHash = passwordEncoder.encode(newPassword);
            userRepository.updatePassword(username, newPasswordHash);
            return true;
        }
    }
}
