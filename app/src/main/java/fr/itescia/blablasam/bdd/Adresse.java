package fr.itescia.blablasam.bdd;

/**
 * Classe Adresse
 */
public class Adresse {
    private int id;
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
     * @param rue
     * @param ville
     * @param codePostal
     * @param pays
     */
    public Adresse(int id, String rue, String codePostal, String ville, String pays){
        this.id = id;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    // Getter - Setter=
    public int get_id() {
        return id;
    }

    public void set_id(Integer id) {
        this.id = id;
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
