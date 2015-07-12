package fr.itescia.blablasam.blablasam;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Gestion de la proposition des trajets
 */
public class ProposerTrajetFragment extends Fragment implements View.OnClickListener{

    //TODO Renommer la classe en ProposerTrajetFragment
    /**
     * Initialisation du fragment
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

    /**
     * Gestion des buttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonValider:
                break;
            default:
                break;
        }
    }
}
