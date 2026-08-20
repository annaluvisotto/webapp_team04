package it.unitn.disi.REST_service_team04.services;

import it.unitn.disi.REST_service_team04.pojos.Esercizio;
import it.unitn.disi.REST_service_team04.pojos.Programma;
import it.unitn.disi.REST_service_team04.repositories.ProgrammiRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProgrammiService {
    private final ProgrammiRepository programmiRepository;

    public ProgrammiService(ProgrammiRepository programmiRepository) {
        this.programmiRepository = programmiRepository;
    }

    public List<Programma> getProgrammiPredef(){
        return programmiRepository.AllDefault();
    }

    public Programma getProgrammaById(int id){
        return programmiRepository.getProgrammaById(id);
    }

    public float calcCalorie(List<Esercizio> listaEs){
        float tot = 0;
        for (Esercizio es : listaEs){
            float kcalUnit = programmiRepository.getKcalByEsercizio(es.getNome());
            if (kcalUnit != 0) {
                float res = kcalUnit * es.getReps() * es.getSerie();
                es.setKcal(res);
                tot += res;
            }
        }
        return tot;
    }



}
