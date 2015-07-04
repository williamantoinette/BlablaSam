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
    private boolean statut;

    /**
     * Constructeur par défaut
     */
    public Utilisateur(){

    }

    /**
     * Constructeur avec arguments
     * @param id
     * @param nom
     * @param prenom
     * @param dateDeNaissance
     * @param adresse
     */
    public Utilisateur(Integer id, String nom, String prenom, String dateDeNaissance, Adresse adresse){
        this._id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        Date date = new Date();
        String maDate = "" + date.getYear() + "" + date.getMonth() + "" + date.getDay();
        this.dateDeNaissance = maDate;
        this.adresse = null;
        this.statut = true;
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

}