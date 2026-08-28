package it.unitn.disi.webapp_team04.services;

import it.unitn.disi.webapp_team04.pojos.Exercise;
import it.unitn.disi.webapp_team04.pojos.Training;
import it.unitn.disi.webapp_team04.pojos.TrainingStats;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TrainingRest {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String progsUrl = "http://localhost:8081/Default-programs";
    private final String progIdUrl = "http://localhost:8081/programs/{id}";
    private final String kcalUrl = "http://localhost:8081/kcal";
    private final String exercisesUrl = "http://localhost:8081/exercises";

    public List<TrainingStats> getAllTrainings() {
        try {
            TrainingStats[] res = restTemplate.getForObject(progsUrl, TrainingStats[].class);
            if (res != null){
                return Arrays.asList(res);
            }
            else{
                return List.of();
            }
        } catch (Exception e) {
            System.err.println("ERRORE CHIAMATA REST (getAllTrainings): " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    public Integer getKcal (List<Exercise> T) {
        if (T == null || T.isEmpty()) {
            return 0;
        }
        try {
            Float res = restTemplate.postForObject(kcalUrl, T, Float.class);
            if (res != null) {
                return Math.round(res);
            }
            else {
                return 0;
            }
        } catch (Exception e) {
            System.err.println("ERRORE CHIAMATA REST (getKcal): " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public Training getTrainingComposition (int id) {
        try {
            Training T = restTemplate.getForObject(progIdUrl, Training.class, id);
            List<Exercise> esercizi = List.of();
            if (T != null) {
                esercizi = T.getEsercizi();
            }
            Integer kcal = getKcal(esercizi);
            T.setKcal(kcal);
            return T;
        } catch (Exception e) {
            System.err.println("ERRORE CHIAMATA REST (getTrainingComposition): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Training> getAllTrainingsComposition () {
        List<TrainingStats> trainingStats = getAllTrainings();
        List<Training> trainings = new ArrayList<>();

        for (TrainingStats ts : trainingStats) {
            Training tr = getTrainingComposition(ts.getId());
            if (tr != null) {
                tr.setNome(ts.getNome());
                trainings.add(tr);
            }
        }
        return trainings;
    }

    public List<Exercise> getAllExercises() {
        try {
            Exercise[] res = restTemplate.getForObject(exercisesUrl, Exercise[].class);
            if (res != null){
                return Arrays.asList(res);
            }
            else{
                return List.of();
            }
        } catch (Exception e) {
            System.err.println("ERRORE CHIAMATA REST (getAllExercises): " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}