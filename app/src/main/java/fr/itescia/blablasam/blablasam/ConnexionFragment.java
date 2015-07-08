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
import java.net.DatagramSocket;
import fr.itescia.blablasam.bdd.Authentification;
import fr.itescia.blablasam.bdd.MyDBHandler;
import fr.itescia.blablasam.bdd.Utilisateur;

/**
 * Gestion de la page de connexion
 */
public class ConnexionFragment extends Fragment implements View.OnClickListener{

    private DatagramSocket socketEnvoi;
    private  DatagramSocket socketReception;

    public ConnexionFragment(){}

    /**
     * Initialisation de la page connexionn
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_connexion, container, false);

        Button buttonConnexion = (Button)rootView.findViewById(R.id.buttonConnexion);
        buttonConnexion.setOnClickListener(this);

        return rootView;
    }

    /**
     * Gestion des bouttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.buttonConnexion: // L'utilisateur souhaite se connecter
                    // Login
                    EditText editTextLogin = (EditText)getActivity().findViewById(R.id.editTextLogin);
                    String login = editTextLogin.getText().toString();
                    // Password
                    EditText editTextPwd = (EditText)getActivity().findViewById(R.id.editTextPwd);
                    String pwd = editTextPwd.getText().toString();


                    Authentification auth = new Authentification(login,pwd,this.getActivity());
                    Thread thread_login = new Thread(auth);
                    thread_login.start();



/*
                    // On cherche l'utilisateur "login"
                    MyDBHandler dbHandler = new MyDBHandler(v.getContext(), null, null, 1);
                    Utilisateur utilisateur = dbHandler.findUtilisateur(login);

                    // On vérifie si on a trouvé un utilisateur
                    if (utilisateur!=null){
                        // On vérifie son mot de passe
                        if(utilisateur.getPassword().equals(pwd)){
                            // Mot de passe ok : utilisateur connecté
                            Toast.makeText(v.getContext(), "Connecté", Toast.LENGTH_SHORT).show();
                            // Connexion
                            Fragment fragment = new Fragment();
                            if(fragment!=null) {
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                            }
                        } else {
                            // Mot de passe incorrect
                            Toast.makeText(v.getContext(), "Login / Password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Aucun compte avec le login inscrit
                        //Toast.makeText(v.getContext(), "Aucun compte avec le login inscrit", Toast.LENGTH_SHORT).show();
                    }*/
                    break;
                default:
                    break;
            }
        } catch (Exception ex){
            System.out.println("Error : "+ ex);
        }
    }
}
