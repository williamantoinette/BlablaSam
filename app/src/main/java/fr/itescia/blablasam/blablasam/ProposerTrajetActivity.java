package fr.itescia.blablasam.blablasam;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import fr.itescia.blablasam.bdd.Adresse;
import fr.itescia.blablasam.bdd.MyDBHandler;
import fr.itescia.blablasam.bdd.Utilisateur;

/**
 * Gestion de la proposition des trajets
 */
public class ProposerTrajetActivity extends Fragment implements View.OnClickListener{

    private static Integer i = 1;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_proposertrajet, container, false);

        Button button = (Button)rootView.findViewById(R.id.buttonValider);
        button.setOnClickListener(this);

        return rootView;
    }

    //TODO Déplacer cette méthode à l'endroit adéquat
    /**
     * Crée un nouvel objet Utilisateur et appelle la méthode pour l'insérer en base de données
     * @param view
     */
    public void newUtilisateur(View view) {
        System.out.println("New user : start");
        MyDBHandler dbHandler = new MyDBHandler(view.getContext(), null, null, 1);
        Adresse adresse = new Adresse(i, 10, "rue des sept quartiers", "78111", "Dammartin", "France");
        dbHandler.addAdresse(adresse);

        Utilisateur utilisateur = new Utilisateur(i++, "Vialon", "Damien", "15011992", adresse);
        dbHandler.addUtilisateur(utilisateur);
        System.out.println("New user : end");
    }


    /**
     * Gestion des buttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonValider:{
                this.newUtilisateur(this.getView());
                break;
            }
            default:{
                break;
            }
        }
    }
}
