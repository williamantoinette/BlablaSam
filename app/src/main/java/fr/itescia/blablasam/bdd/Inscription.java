package fr.itescia.blablasam.bdd;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import fr.itescia.blablasam.blablasam.R;

public class Inscription implements Runnable {
   /* private String login;
    private String password;
    private String nom;
    private String prenom;
    //private String adresse;
    private String DateNaissance;
    private Adresse adresse;*/
    private Utilisateur utilisateur;
    private Activity activity;


    public Inscription(Utilisateur utilisateur, Activity activity){
        /*this.login = utilisateur.getLogin();
        this.password = utilisateur.getPassword();
        this.nom = utilisateur.getNom();
        this.prenom = utilisateur.getPrenom();
        this.DateNaissance = utilisateur.getDateDeNaissance();
        this.adresse = utilisateur.getAdresse();*/
        this.utilisateur = utilisateur;
        this.activity = activity;

    }

    @Override
    public void run() {

        Gson serialiseur = new Gson();
        JsonElement jsonElement = serialiseur.toJsonTree(this.utilisateur);
        try
        {

           if(Server.sendJSONPost("/inscription", jsonElement).trim().equals("OK"))
            {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"L'inscription s'est déroulée correctement ! ", Toast.LENGTH_SHORT).show();

                    }
                });
            }
    }
        catch (Exception ex)
        {
            System.out.print("Err 1  "+ ex.getMessage());
        }


    }
}

