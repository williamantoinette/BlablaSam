package fr.itescia.blablasam.bdd;

import java.util.Date;

/**
 * Classe Utilisateur
 */
public class Utilisateur{
    private Integer _id;
    private String nom;
    private String prenom;
    private String dateDeNaissance;
    private String dateInscription;
    private Adresse adresse;
    private String adresseBis;
    private boolean statut;
    private String login;
    private String password;

    /**
     * Constructeur par défaut
     */
    public Utilisateur(){

    }

    /**
     * Constructeur avec arguments
     * @param nom
     * @param prenom
     * @param dateDeNaissance

     */
    public Utilisateur(String nom, String prenom, String dateDeNaissance, Adresse adresse, String login, String password){
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        Date date = new Date();
        String maDate = "" + date.getYear() + " " + date.getMonth() +  "" + date.getDay();
        this.dateDeNaissance = maDate.trim();
        this.adresse = adresse;
        this.statut = true;
        this.login = login;
        this.password = password;
    }

    // Getter - Setter
    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdresseBis() {
        return adresseBis;
    }

    public void setAdresseBis(String adresseBis) {
        this.adresseBis = adresseBis;
    }
}