package fr.itescia.blablasam.blablasam;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import fr.itescia.blablasam.bdd.Adresse;
import fr.itescia.blablasam.bdd.Trajet;

/**
 * Classe pour l'affichage de la liste de ses trajets
 */
public class MesTrajetsFragment extends Fragment{

    private ListView listViewTrajet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mestrajets, container, false);

        listViewTrajet = (ListView)rootView.findViewById(R.id.list);

        // Tableau pour afficher des valeurs dans la listView
        //TODO remplacer cet objet par la requête en BDD
        Trajet[] trajets = new Trajet[]{
                new Trajet(1, new Adresse(1, "Rue des sept quartiers", "78111", "Dammartin-en-Serve", "France"), new Adresse(2, "10 avenue de l'Entreprise", "95000", "Cergy", "France")),
                new Trajet(2, new Adresse(3, "10 avenue de l'Entreprise", "95000", "Cergy", "France"), new Adresse(4, "Rue des sept quartiers", "78111", "Dammartin-en-Serve", "France"))
        };

        // Context - Layout for the row - ID of the TextView to which the data is written - the Array of data
        ArrayAdapter<Trajet> adapter = new ArrayAdapter<Trajet>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, trajets);
        listViewTrajet.setAdapter(adapter);

        // On Click Listener sur la listView
        listViewTrajet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = listViewTrajet.getItemAtPosition(position).toString();
                String[] splitItem = itemValue.split(":");
                String trajetId = splitItem[0].trim();

                Toast.makeText(getActivity().getApplicationContext(), "Position : " + position + "  ID trajet : " + trajetId, Toast.LENGTH_SHORT).show();
                //TODO Gérer une action et supprimer le Toast
            }
        });

        return rootView;
    }

}
