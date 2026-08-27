package it.unitn.disi.webapp_team04.repositories;

import it.unitn.disi.webapp_team04.pojos.Recensione;
import it.unitn.disi.webapp_team04.rowmappers.RecensioneRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RecensioneRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecensioneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void addRecensione(Recensione r){
        String sql ="INSERT INTO Reviews(ID_User, title, text) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, r.getId_user(), r.getTitolo(), r.getTesto());
    }

    public List<Recensione> getRecensioni(){
        String sql = "SELECT r.title, r.text, u.username, r.ID_User FROM Reviews r JOIN Users u ON u.ID = r.ID_User ORDER BY RAND()";
        return jdbcTemplate.query(sql, new RecensioneRowMapper());
    }
}
