package it.unitn.disi.webapp_team04.pojos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//pojo per inserire un utente nel db a seguito della registrazione
public class User {

    private Integer id;
    private String nome;
    private String cognome;
    private String data_nascita;
    private String email;
    private String username;
    private String password;
    private String ruolo;
    private String data_reg;

    public User(String nome, String ruolo, String password, String username, String email, String data_nascita, String cognome, String data_reg) {
        this.id = 0;
        this.nome = nome;
        this.ruolo = ruolo;
        this.password = password;
        this.username = username;
        this.email = email;
        this.data_nascita = data_nascita;
        this.cognome = cognome;
        this.data_reg = data_reg;
    }

    public User() {
        this.id = 0;
        this.nome = "";
        this.ruolo = "";
        this.password = "";
        this.username = "";
        this.email = "";
        this.data_nascita = "";
        this.cognome = "";
        this.data_reg = "";
    }

    //setter
    public void setData_nascita(String data_nascita) {
        this.data_nascita = data_nascita;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setData_reg(String data_reg){this.data_reg = data_reg;}

    //getter
    public String getEmail() {
        return email;
    }
    public String getData_nascita() {
        return data_nascita;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getRuolo() {
        return ruolo;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Integer getId() {
        return id;
    }
    public String getData_reg(){return data_reg;}

    public String getRuoloDisplay(){
        switch (this.ruolo) {
            case "ROLE_ADMIN":
                return "ADMIN";
            case "ROLE_USER_PROVA":
                return "PROVA";
            case "ROLE_USER_BASIC":
                return "BASIC";
            case "ROLE_USER_PRO":
                return "PRO";
            default:
                return this.ruolo;
        }
    }

    public String getDataDisplay() {
        LocalDate data = LocalDate.parse(this.data_nascita);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(f);
    }

    public String getData2Display() {
        LocalDate data = LocalDate.parse(this.data_reg);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(f);
    }
}
