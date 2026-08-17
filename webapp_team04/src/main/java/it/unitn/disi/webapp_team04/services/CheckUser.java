package it.unitn.disi.webapp_team04.services;

import it.unitn.disi.webapp_team04.pojos.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CheckUser {
    private final JdbcTemplate jdbcTemplate;

    public CheckUser(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //questo controllo verrà eseguito dal controller
    public boolean checkUsername(String username){
        String check = "SELECT COUNT(*) FROM Users WHERE Username=?";
        Integer count = jdbcTemplate.queryForObject(check, Integer.class, username);
        return (count > 0); //torna true se è già presente un utente con lo stesso username
    }

    public void addUser(User user){
        //per convertire la data di nascita da GG/MM/AAAA a AAAA/MM/GG
        String data_nascita = user.getData_nascita();
        DateTimeFormatter traduttore = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data_nascita_conv = LocalDate.parse(data_nascita, traduttore);

        //inserimento in Users
        String sqlUsers ="INSERT INTO Users(username, password, enabled) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlUsers, user.getUsername(), user.getPassword(), true);

        //inserimento in Authorities
        String sqlAuth ="INSERT INTO Authorities(username, authority) VALUES (?, ?)";
        jdbcTemplate.update(sqlAuth, user.getUsername(), user.getAuthority());

        //recupero l'id dello user, sfruttando il fatto che username è unique
        String sqlId = "SELECT ID FROM Users WHERE username = ?";
        Integer id = jdbcTemplate.queryForObject(sqlId, Integer.class, user.getUsername());

        //inserimento in Users_info
        String sqlInfo ="INSERT INTO Users_info VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlInfo, id, user.getNome(), user.getCognome(), java.sql.Date.valueOf(data_nascita_conv), user.getEmail(), java.sql.Date.valueOf(java.time.LocalDate.now()));
    }
}
