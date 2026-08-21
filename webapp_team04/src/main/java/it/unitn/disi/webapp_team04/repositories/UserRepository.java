package it.unitn.disi.webapp_team04.repositories;

import it.unitn.disi.webapp_team04.pojos.SecurityUser;
import it.unitn.disi.webapp_team04.pojos.User;
import it.unitn.disi.webapp_team04.rowmappers.ProfiloRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserRepository(JdbcTemplate jdbcTemplate, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
    }

    //questo controllo verrà eseguito dal controller
    public boolean checkUsername(String username){
        return userDetailsManager.userExists(username);
    }

    @Transactional
    public void addUser(User user){
        //per convertire la data di nascita da GG/MM/AAAA a AAAA/MM/GG
        String data_nascita = user.getData_nascita();
        DateTimeFormatter traduttore = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data_nascita_conv = LocalDate.parse(data_nascita, traduttore);

        //hashing della password prima di inserire l'utente in Users
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsManager.createUser(new SecurityUser(user));

        //inserimento in Users_info, estraendo l'id da Users
        String sqlId = "SELECT id FROM Users WHERE username=?";
        Integer id = jdbcTemplate.queryForObject(sqlId, Integer.class, user.getUsername());
        String sqlInfo ="INSERT INTO Users_info VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlInfo, id, user.getUsername(), user.getNome(), user.getCognome(), java.sql.Date.valueOf(data_nascita_conv), user.getEmail(), java.sql.Date.valueOf(java.time.LocalDate.now()));
    }

    public User getUser(String username){
        String sql = "SELECT nome, cognome, data_nascita, authority FROM Users_info u, Authorities a WHERE u.username = a.username AND u.username=?";
        return jdbcTemplate.queryForObject(sql, new ProfiloRowMapper(), username);
    }

}
