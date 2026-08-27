package it.unitn.disi.webapp_team04.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Training {
    private int id;
    private String nome;
    private int kcal;
    private List<Exercise> esercizi = new ArrayList<>();

    @JsonCreator
    public Training() {
    }

    public Training(int id, String nome, int kcal, List<Exercise> esercizi) {
        this.id = id;
        this.nome = nome;
        this.kcal = kcal;
        if (esercizi != null) {
            this.esercizi = esercizi;
        }
        else {
            this.esercizi = new ArrayList<>();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public List<Exercise> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(List<Exercise> esercizi) {
        if (esercizi != null) {
            this.esercizi = esercizi;
        }
        else {
            this.esercizi = new ArrayList<>();
        }
    }
}