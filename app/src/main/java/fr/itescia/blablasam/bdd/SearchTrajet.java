package fr.itescia.blablasam.bdd;

import android.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import fr.itescia.blablasam.blablasam.R;
import fr.itescia.blablasam.blablasam.SearchFragment;

public class SearchTrajet implements Runnable {
    private String departTrajet;
    private String destinationTrajet;
    private String dateTrajet;
    //private String heureDebut;
    private String heureFin;
    private Trajet[] trajets;



    public SearchTrajet(String destinationTrajet, String dateTrajet,String heureFin, String departTrajet){
        this.destinationTrajet = destinationTrajet;
        this.dateTrajet = dateTrajet;
        this.heureFin = heureFin;
        this.departTrajet = departTrajet;
    }

    public SearchTrajet()
    {

    }

    @Override
    public void run() {
        try{
            Gson serialiser = new Gson();
            //Supprime les caractères interdit dans l'URL
            this.destinationTrajet = this.destinationTrajet.replace(" ","");
            this.destinationTrajet = this.destinationTrajet.replace("''","");

            // Si la requete se déroule bien
            JSONArray tableauTrajet = new JSONArray(Server.sendGet("/SearchTrajet", "" +
                    "destination=" + this.destinationTrajet +
                    "&depart=" + this.departTrajet +
                    "&datefete=" + this.dateTrajet +
                    "&heureFin=" + this.heureFin));

            JsonParser jsonParser = new JsonParser();
            System.out.println("Nombre de trajet trouvé : " + tableauTrajet.length());
            Trajet [] trajets = new Trajet[tableauTrajet.length()];
            for(int i = 0; i < tableauTrajet.length();i++)
            {
                Trajet t  = new Trajet();

                JsonElement element = jsonParser.parse(tableauTrajet.get(i).toString());
                JsonObject obj = element.getAsJsonObject();
                t.set_id(obj.get("id").getAsInt());
                trajets[i] = t;

            }
            this.trajets = trajets;



        }
        catch (Exception ex ){
            System.out.println("ERR : " +ex);
        }
    }

    public String getDepartTrajet() {
        return departTrajet;
    }

    public void setDepartTrajet(String departTrajet) {
        this.departTrajet = departTrajet;
    }

    public String getDestinationTrajet() {
        return destinationTrajet;
    }

    public void setDestinationTrajet(String destinationTrajet) {
        this.destinationTrajet = destinationTrajet;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getDateTrajet() {
        return dateTrajet;
    }

    public void setDateTrajet(String dateTrajet) {
        this.dateTrajet = dateTrajet;
    }

    public synchronized Trajet[] getTrajets() {
        return trajets;
    }

    public synchronized void setTrajets(Trajet[] trajets) {
        this.trajets = trajets;
    }
}
