package fr.itescia.blablasam.blablasam;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.itescia.blablasam.bdd.Inscription;
import fr.itescia.blablasam.bdd.MyDBHandler;
import fr.itescia.blablasam.bdd.Utilisateur;

/**
 * Gestion de la page d'inscription
 */
public class InscriptionFragment extends Fragment implements View.OnClickListener{

    private View rootView;
    /**
     * Initialisation de la page inscription
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_inscription, container, false);

        Button buttonValider = (Button)rootView.findViewById(R.id.buttonValider);
        buttonValider.setOnClickListener(this);

        return rootView;
    }

    /**
     * Gestion des bouttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonValider:




                // On récupère les valeurs saisies
                EditText editTextLogin = (EditText)getActivity().findViewById(R.id.editTextLogin);
                String login = editTextLogin.getText().toString();

                EditText editTextPwd = (EditText)getActivity().findViewById(R.id.editTextPwd);
                String pwd = editTextPwd.getText().toString();

                EditText editTextNom = (EditText)getActivity().findViewById(R.id.editTextNom);
                String nom = editTextNom.getText().toString();

                EditText editTextPrenom = (EditText)getActivity().findViewById(R.id.editTextPrenom);
                String prenom = editTextPrenom.getText().toString();

                EditText editTextDateDeNaissance = (EditText)getActivity().findViewById(R.id.editTextDateDeNaissance);
                String dateDeNaissance = editTextDateDeNaissance.getText().toString();

                EditText editTextAdresse = (EditText)getActivity().findViewById(R.id.editTextAdresse);
                String adresse = editTextAdresse.getText().toString();

                // Création de l'utilisateur
                MyDBHandler dbHandler = new MyDBHandler(v.getContext(), null, null, 1);
                Utilisateur utilisateur = new Utilisateur(nom, prenom, dateDeNaissance, adresse, login, pwd);

                try {

                    Inscription inscription = new Inscription(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAdresseBis(), utilisateur.getDateDeNaissance(), utilisateur.getLogin(), utilisateur.getPassword(), MainActivity.getSocketEnvoi());
                    Thread thread_inscription = new Thread(inscription);
                    thread_inscription.start();
                }
                catch(Exception ex)
                {

                }
                // On vérifie si l'utilisateur "login" n'existe pas
                Utilisateur verif = dbHandler.findUtilisateur(login);
                if(verif==null){
                    dbHandler.addUtilisateur(utilisateur);
                    Toast.makeText(v.getContext(), "Création du compte ok", Toast.LENGTH_SHORT).show();

                    // Retour à la page d'accueil
                    Fragment fragment = new HomeFragment();
                    if(fragment!=null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    }
                } else {
                    Toast.makeText(v.getContext(), "Login déjà existant", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }
}
