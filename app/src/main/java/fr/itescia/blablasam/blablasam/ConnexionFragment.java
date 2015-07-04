package fr.itescia.blablasam.blablasam;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Gestion de la page de connexion
 */
public class ConnexionFragment extends Fragment {

    public ConnexionFragment(){}

    /**
     * Initialisation de la page connexionn
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connexion, container, false);
        return rootView;


    }
}
