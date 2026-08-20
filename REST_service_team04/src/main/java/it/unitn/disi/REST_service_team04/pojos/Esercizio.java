package it.unitn.disi.REST_service_team04.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Esercizio {
    private String nome;
    private int serie;
    private int reps;
    private Float kcal;

    public Esercizio(){}

    public Esercizio(String nome, int serie, int reps, Float kcal){
        this.nome = nome;
        this.serie = serie;
        this.reps = reps;
        this.kcal = kcal;
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

    public Float getKcal() {
        return kcal;
    }

    public void setKcal(Float kcal) {
        this.kcal = kcal;
    }
}
