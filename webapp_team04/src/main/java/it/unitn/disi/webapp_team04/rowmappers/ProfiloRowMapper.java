package it.unitn.disi.webapp_team04.rowmappers;

import it.unitn.disi.webapp_team04.pojos.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfiloRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet r, int i) throws SQLException {
        User u = new User();
        u.setNome(r.getString("nome"));
        u.setCognome(r.getString("cognome"));
        u.setData_nascita(r.getString("data_nascita"));
        u.setRuolo(r.getString("authority"));
        return u;
    }
}
