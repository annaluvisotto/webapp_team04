package it.unitn.disi.webapp_team04.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainingStats {
    private int id;
    private String nome;
    private int exec = 0;

    @JsonCreator
    public TrainingStats() {
        exec = 0;
    }

    public TrainingStats (int id, String nome, int exec){
        this.id = id;
        this.nome = nome;
        this.exec = exec;
    }

    public int getId() {return id;}
    public String getNome(){return this.nome;}
    public int getExec(){return this.exec;}

    public void setId(int id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
    public void setExec(Integer exec) {
        if (exec!=null)
        {
            this.exec = exec;
        }
        else {
            this.exec = 0;
        }
    }
}
