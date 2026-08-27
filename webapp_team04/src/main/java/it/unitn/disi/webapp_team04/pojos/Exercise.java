package it.unitn.disi.webapp_team04.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Exercise {
    private String nome;
    private int serie;
    private int reps;

    @JsonCreator
    public Exercise() {
    }

    public Exercise(String nome, int serie, int reps) {
        this.nome = nome;
        this.serie = serie;
        this.reps = reps;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}