package it.unitn.disi.REST_service_team04.repositories;

import it.unitn.disi.REST_service_team04.pojos.Esercizio;
import it.unitn.disi.REST_service_team04.pojos.Programma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProgrammiRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public ProgrammiRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Programma> AllDefault() {
        String sqlProgrammi = "SELECT ID_Training, nome_allenamento FROM Default_Trainings";
        RowMapper<Programma> rowMapper = (res, righe) -> new Programma(
                res.getInt("ID_Training"),
                res.getString("nome_allenamento")
        );
        return jdbc.query(sqlProgrammi, rowMapper);
    }

    public Programma getProgrammaById(int id){
        // controllo che l'id cercato esista perché la richiesta http del client potrebbe non essere soddisfacibile
        String sqlProgramma = "SELECT ID_Training, nome_allenamento FROM Default_Trainings WHERE ID_Training = ?";
        List<Programma> progs = jdbc.query(
            sqlProgramma,
            (res, righe) -> new Programma(res.getInt("ID_Training"), res.getString("nome_allenamento")),
            id
        );

        if (progs.isEmpty()) {
            return null;
        }

        Programma selectedProg = progs.getFirst();
        String sqlEsercizi = "SELECT nome_esercizio, numero_serie, numero_ripetizioni, kcal FROM Default_Trainings_Info WHERE ID_Training = ?";
        RowMapper<Esercizio> esercizioRowMapper = (rs, rowNum) -> new Esercizio(
                rs.getString("nome_esercizio"),
                rs.getInt("numero_serie"),
                rs.getInt("numero_ripetizioni"),
                rs.getFloat("kcal")
        );
        List<Esercizio> esercizi = jdbc.query(sqlEsercizi, esercizioRowMapper, id);
        selectedProg.setEsercizi(esercizi);

        return selectedProg;
    }

    public Float getKcalByEsercizio(String Esercizio) {
        String sqlCalorie = "SELECT kcal FROM Exercises WHERE nome_esercizio = ?";
        List <Float> kcal = jdbc.query(
                sqlCalorie,
                (res, righe) -> res.getFloat("kcal"),
                Esercizio
        );

        if (kcal.isEmpty()) {
            return null;
        }
        else {
            return kcal.getFirst();
        }
    }
}
