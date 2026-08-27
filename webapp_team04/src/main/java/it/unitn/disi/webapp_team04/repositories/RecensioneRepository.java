package it.unitn.disi.webapp_team04.repositories;

import it.unitn.disi.webapp_team04.pojos.Recensione;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RecensioneRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecensioneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void addRecensione(Recensione r){
        String sql ="INSERT INTO Reviews VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, r.getId_user(), r.getTitolo(), r.getTesto());
    }
}
