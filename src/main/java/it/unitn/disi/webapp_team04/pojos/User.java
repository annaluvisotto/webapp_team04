package it.unitn.disi.webapp_team04.pojos;

//pojo per inserire un utente nel db a seguito della registrazione
public class User {

    private String nome;
    private String cognome;
    private String data_nascita;
    private String email;
    private String username;
    private String password;
    private String authority;

    //setter
    public void setData_nascita(String data_nascita) {
        this.data_nascita = data_nascita;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
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
    public String getAuthority() {
        return authority;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
