package fr.itescia.blablasam.blablasam;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import fr.itescia.blablasam.bdd.Trajet;

public class ListeTrajetFragment extends Fragment {

    private DialogInterface.OnClickListener dialogClickListener;
    private ListView listViewTrajet;
    private String numeroTelephone = "0664141739";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liste_trajet, container, false);

        listViewTrajet = (ListView) rootView.findViewById(R.id.listeTrajet);

        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                Trajet[] trajets = (Trajet[]) bundle.getSerializable("trajets");
                System.out.println("Récupération du bundle réussie");
                for(int i = 0; i < trajets.length;i++)
                {
                    System.out.println("Elem : " + trajets[i].get_id());
                }
                ArrayAdapter<Trajet> adapter = new ArrayAdapter<Trajet>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, trajets);
                listViewTrajet.setAdapter(adapter);
            }

        }
        catch(Exception ex)
        {
            System.err.println("Bundle err  :" + ex.getMessage());
        }
      /*  Trajet[] trajets = new Trajet[]{
                new Trajet(1, new Adresse(1, "Rue des sept quartiers", "78111", "Dammartin-en-Serve", "France"), new Adresse(2, "10 avenue de l'Entreprise", "95000", "Cergy", "France")),
                new Trajet(2, new Adresse(3, "10 avenue de l'Entreprise", "95000", "Cergy", "France"), new Adresse(4, "Rue des sept quartiers", "78111", "Dammartin-en-Serve", "France"))
    };*/




        // On Click Listener sur la listView
        listViewTrajet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = listViewTrajet.getItemAtPosition(position).toString();
                String[] splitItem = itemValue.split(":");
                String trajetId = splitItem[0].trim();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Souhaitez-vous vous inscrire pour ce trajet ?").setPositiveButton("Oui", dialogClickListener).setNegativeButton("Non", dialogClickListener).show();
            }
        });

        // Boite de dialogue Oui / Non
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // Envoi d'un SMS
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(numeroTelephone, null, "Bonjour, je viens de m'inscrire sur votre trajet. Vous pouvez me joindre à ce numéro.", null, null);
                        Toast.makeText(getActivity(), "Inscription ok", Toast.LENGTH_SHORT).show();

                        // Redirection
                        Fragment fragment = new MesTrajetsFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        return rootView;

    }
}

