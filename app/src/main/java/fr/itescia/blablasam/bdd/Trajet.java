package fr.itescia.blablasam.bdd;

import java.util.List;
import java.util.Date;

/**
 * Classe Trajet
 */
public class Trajet {
    private Integer _id;
    private Adresse depart;
    private Adresse destination;
    private Utilisateur conducteur;
    private List<Utilisateur> passagers;
    private  int nombrePlace;
    private int nombrePlaceRestante;
    private int detourMax;
    private Date Date;

    /**
     * Constructeur par défaut
     */
    public Trajet(){

    }

    /**
     * Constructeur avec arguments (sans passager)
     * @param id
     * @param depart
     * @param destination
     * @param conducteur
     */
    public Trajet(Integer id, Adresse depart, Adresse destination, Utilisateur conducteur){
        this._id = id;
        this.depart = depart;
        this.destination = destination;
        this.conducteur = conducteur;
        this.passagers = null;
    }

    /**
     * Constructeur avec arguments (avec passagers)
     * @param id
     * @param depart
     * @param destination
     * @param conducteur
     * @param passagers
     */
    public Trajet(Integer id, Adresse depart, Adresse destination, Utilisateur conducteur, List<Utilisateur> passagers){
        this._id = id;
        this.depart = depart;
        this.destination = destination;
        this.conducteur = conducteur;
        this.passagers = passagers;
    }

    // Getter - Setter
    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Adresse getDepart() {
        return depart;
    }

    public void setDepart(Adresse depart) {
        this.depart = depart;
    }

    public Adresse getDestination() {
        return destination;
    }

    public void setDestination(Adresse destination) {
        this.destination = destination;
    }

    public Utilisateur getConducteur() {
        return conducteur;
    }

    public void setConducteur(Utilisateur conducteur) {
        this.conducteur = conducteur;
    }

    public List<Utilisateur> getPassagers() {
        return passagers;
    }

    public void setPassagers(List<Utilisateur> passagers) {
        this.passagers = passagers;
    }

    public int getNombrePlace() {
        return nombrePlace;
    }

    public void setNombrePlace(int nombrePlace) {
        this.nombrePlace = nombrePlace;
    }

    public int getNombrePlaceRestante() {
        return nombrePlaceRestante;
    }

    public void setNombrePlaceRestante(int nombrePlaceRestante) {
        this.nombrePlaceRestante = nombrePlaceRestante;
    }

    public int getDetourMax() {
        return detourMax;
    }

    public void setDetourMax(int detourMax) {
        this.detourMax = detourMax;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }
}
