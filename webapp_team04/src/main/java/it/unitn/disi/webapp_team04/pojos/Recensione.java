package it.unitn.disi.webapp_team04.pojos;

public class Recensione {
    private String titolo;
    private String testo;
    private Integer id_user;
    private String username;

    public Recensione(String titolo, String testo, Integer id_user, String username) {
        this.titolo = titolo;
        this.testo = testo;
        this.id_user = id_user;
        this.username = username;
    }

    public Recensione() {
        this.titolo = "";
        this.testo = "";
        this.id_user = 0;
        this.username = "";
    }

    public String getTitolo() {
        return titolo;
    }

    public String getTesto() {
        return testo;
    }

    public Integer getId_user() {
        return id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
