package it.unitn.disi.webapp_team04.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import it.unitn.disi.webapp_team04.pojos.TrainingStats;
import java.util.List;

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

    public List<List<TrainingStats>> gatAdminStats()
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
}
