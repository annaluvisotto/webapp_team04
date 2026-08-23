package it.unitn.disi.webapp_team04.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingRepository {
    private final JdbcTemplate jdbcTemplate;

    public TrainingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getExec(String username){
        String sqlId = "SELECT id FROM Users WHERE username=?";
        String idUser = jdbcTemplate.queryForObject(sqlId, String.class, username);
        String sqlExec = "SELECT SUM(esecuzioni) FROM Default_Trainings_Exec WHERE ID_User=?";
        return jdbcTemplate.queryForObject(sqlExec, Integer.class, idUser);
    }
}
