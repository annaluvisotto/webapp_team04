package it.unitn.disi.webapp_team04.pojos;

public class TrainingStats {
    private int id;
    private String nome;
    private int exec;

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
    public void setExec(){this.exec = exec;}
}
