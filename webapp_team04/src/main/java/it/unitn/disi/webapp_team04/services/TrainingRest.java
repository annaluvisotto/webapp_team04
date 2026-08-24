package it.unitn.disi.webapp_team04.services;

import it.unitn.disi.webapp_team04.pojos.TrainingStats;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class TrainingRest {

    private final RestTemplate restTemplate = new RestTemplate();

    // Inserisci l'URL esatto del tuo endpoint REST
    private final String baseUrl = "http://localhost:8081/Default-programs";

    public List<TrainingStats> getAllTrainings() {
        try {
            TrainingStats[] res = restTemplate.getForObject(baseUrl, TrainingStats[].class);
            if (res != null){
                return Arrays.asList(res);
            }
            else{
                return List.of();
            }
        } catch (Exception e) {
            System.err.println("ERRORE CHIAMATA REST: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}