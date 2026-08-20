package it.unitn.disi.REST_service_team04.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Programma {
    private int id;
    private String nome;
    private List<Esercizio> esercizi = new ArrayList<>();

    public Programma() {}

    public Programma(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Programma(int id, String nome, List<Esercizio> esercizi) {
        this.id = id;
        this.nome = nome;
        if (esercizi != null) {
            this.esercizi = esercizi;
        } else{
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

    public List<Esercizio> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }
}
