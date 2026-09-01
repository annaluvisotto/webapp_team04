package it.unitn.disi.webapp_team04.repositories;

import it.unitn.disi.webapp_team04.pojos.Exercise;
import it.unitn.disi.webapp_team04.pojos.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import it.unitn.disi.webapp_team04.pojos.TrainingStats;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TrainingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TrainingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<List<TrainingStats>> getAdminStats()
    {
        String sql = """
                WITH avgB AS (
                    SELECT dte.ID_Training, CAST(ROUND(AVG(dte.esecuzioni)) AS INT) AS media_basic
                    FROM Default_Trainings_Exec dte
                    JOIN Users u ON dte.ID_User = u.ID
                    JOIN Authorities a ON u.username = a.username
                    WHERE a.authority = 'ROLE_USER_BASIC'
                    GROUP BY dte.ID_Training
                ),
                avgP AS (
                    SELECT dte.ID_Training, CAST(ROUND(AVG(dte.esecuzioni)) AS INT) AS media_pro
                    FROM Default_Trainings_Exec dte
                    JOIN Users u ON dte.ID_User = u.ID
                    JOIN Authorities a ON u.username = a.username
                    WHERE a.authority = 'ROLE_USER_PRO'
                    GROUP BY dte.ID_Training
                ),
                all_trainings AS (
                    SELECT DISTINCT ID_Training FROM Default_Trainings_Exec
                )
                SELECT t.ID_Training, b.media_basic, p.media_pro
                FROM all_trainings t
                LEFT JOIN avgB b ON t.ID_Training = b.ID_Training
                LEFT JOIN avgP p ON t.ID_Training = p.ID_Training
                ORDER BY t.ID_Training ASC
                """;
        return jdbcTemplate.query(sql, (res, dim) -> {
            int id = res.getInt("ID_Training");
            int exec_basic = res.getInt("media_basic");
            int exec_pro = res.getInt("media_pro");

            TrainingStats statBasic = new TrainingStats(id, "", exec_basic);
            TrainingStats statPro = new TrainingStats(id, "", exec_pro);

            return List.of(statBasic, statPro);
        });
    }

    public List<TrainingStats> getDefaultTrainingsStats(String username) {
        String sql = """
            SELECT dte.ID_Training, dte.esecuzioni
            FROM Default_Trainings_Exec dte
            JOIN Users u ON dte.ID_User = u.ID
            WHERE u.username = ?
            ORDER BY dte.ID_Training ASC
            """;

        return jdbcTemplate.query(sql, (res, dim) -> {
            int id = res.getInt("ID_Training");
            int exec = res.getInt("esecuzioni");
            return new TrainingStats(id, "", exec);
        }, username);
    }

    public List<TrainingStats> getPersonalizedTrainingsStats(String username) {
        String sql = """
            SELECT pte.ID_Training, pte.nome_allenamento, pte.esecuzioni
            FROM Personalized_Trainings_Exec pte
            JOIN Users u ON pte.ID_User = u.ID
            WHERE u.username = ?
            ORDER BY pte.ID_Training ASC
            """;

        return jdbcTemplate.query(sql, (res, dim) -> {
            int id = res.getInt("ID_Training");
            String nome = res.getString("nome_allenamento");
            int exec = res.getInt("esecuzioni");
            return new TrainingStats(id, nome, exec);
        }, username);
    }

    public List<Training> getPersonalizedTrainings(String username) {
        String sqlTrainings = """
            SELECT pte.ID_Training, pte.nome_allenamento
            FROM Personalized_Trainings_Exec pte
            JOIN Users u ON pte.ID_User = u.ID
            WHERE u.username = ?
            ORDER BY pte.ID_Training ASC
            """;

        return jdbcTemplate.query(sqlTrainings, (res, dim) -> {
            int id = res.getInt("ID_Training");
            String nome = res.getString("nome_allenamento");
            int kcal = 0;

            String sqlExercises = """
                SELECT nome_esercizio, numero_serie, numero_ripetizioni
                FROM Personalized_Trainings_Info
                WHERE ID_Training = ?
                """;

            List<Exercise> esercizi = jdbcTemplate.query(sqlExercises, (rsEx, rNum) -> new Exercise(
                    rsEx.getString("nome_esercizio"),
                    rsEx.getInt("numero_serie"),
                    rsEx.getInt("numero_ripetizioni")
            ), id);

            return new Training(id, nome, kcal, esercizi);
        }, username);
    }

    public int getTotalExecutions(String username) {
        String sql = """
            SELECT SUM(dte.esecuzioni)
            FROM Default_Trainings_Exec dte
            JOIN Users u ON dte.ID_User = u.ID
            WHERE u.username = ?
            """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (count != null) {
            return count;
        }
        else {
            return 0;
        }
    }

    @Transactional
    public void incrementDefaultExec(String username, int trainingId) {
        String sqlId = "SELECT id FROM Authorities WHERE username = ?";
        Integer userId = jdbcTemplate.queryForObject(sqlId, Integer.class, username);

        String sqlUpdate = "UPDATE Default_Trainings_Exec SET esecuzioni = esecuzioni + 1 WHERE ID_User = ? AND ID_Training = ?";
        int rows = jdbcTemplate.update(sqlUpdate, userId, trainingId);
        if (rows == 0) {
            String sqlInsert = "INSERT INTO Default_Trainings_Exec (ID_User, ID_Training, esecuzioni) VALUES (?, ?, 1)";
            jdbcTemplate.update(sqlInsert, userId, trainingId);
        }
    }

    @Transactional
    public void incrementPersonalizedExec(String username, int trainingId) {
        String sqlId = "SELECT id FROM Users WHERE username = ?";
        Integer userId = jdbcTemplate.queryForObject(sqlId, Integer.class, username);

        String sqlUpdate = "UPDATE Personalized_Trainings_Exec SET esecuzioni = esecuzioni + 1 WHERE ID_User = ? AND ID_Training = ?";
        jdbcTemplate.update(sqlUpdate, userId, trainingId);
    }

    public boolean uniquePersonalizedTraining(String username, String nomeAllenamento) {
        String sql = "SELECT COUNT(*) FROM Personalized_Trainings_Exec pte JOIN Users u ON pte.ID_User = u.ID WHERE u.username = ? AND pte.nome_allenamento = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, nomeAllenamento.trim());
        return (count != null && count == 0);
    }

    @Transactional
    public void savePersonalizedTraining(String username, Training training) {
        String sqlUser = "SELECT ID FROM Users WHERE username = ?";
        Integer userId = jdbcTemplate.queryForObject(sqlUser, Integer.class, username);

        String sqlTraining = "INSERT INTO Personalized_Trainings_Exec (ID_User, nome_allenamento, esecuzioni) VALUES (?, ?, 0)";
        jdbcTemplate.update(sqlTraining, userId, training.getNome());

        String sqlTrainingId = "SELECT ID_Training FROM Personalized_Trainings_Exec WHERE ID_User = ? AND nome_allenamento = ?";
        int trainingId = jdbcTemplate.queryForObject(sqlTrainingId, Integer.class, userId, training.getNome());

        if (training.getEsercizi() != null && !training.getEsercizi().isEmpty()) {
            String sqlExInfo = """
                INSERT INTO Personalized_Trainings_Info (ID_Training, ID_user, nome_esercizio, numero_serie, numero_ripetizioni)
                VALUES (?, ?, ?, ?, ?)
                """;

            for (Exercise ex : training.getEsercizi()) {
                jdbcTemplate.update(sqlExInfo,
                        trainingId,
                        userId,
                        ex.getNome(),
                        ex.getSerie(),
                        ex.getReps()
                );
            }
        }
    }
}
