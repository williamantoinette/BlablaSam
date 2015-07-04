package fr.itescia.blablasam.bdd;

/**
 * Classe Adresse
 */
public class Adresse {
    private int _id;
    private int numero;
    private String rue;
    private String codePostal;
    private String ville;
    private String pays;

    /**
     * Constructeur par défaut
     */
    public Adresse(){

    }

    /**
     * Constructeur avec arguments
     * @param id
     * @param numero
     * @param rue
     * @param ville
     * @param codePostal
     * @param pays
     */
    public Adresse(int id, int numero, String rue, String codePostal, String ville, String pays){
        this._id = id;
        this.numero = numero;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    // Getter - Setter=
    public int get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

}
