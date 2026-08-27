package it.unitn.disi.webapp_team04.rowmappers;

import it.unitn.disi.webapp_team04.pojos.Recensione;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecensioneRowMapper implements RowMapper<Recensione> {
    public Recensione mapRow(ResultSet r, int i) throws SQLException {
        Recensione rec = new Recensione();
        rec.setTitolo(r.getString("title"));
        rec.setTesto(r.getString("text"));
        rec.setUsername(r.getString("username"));
        rec.setId_user(r.getInt("ID_User"));
        return rec;
    }
}
