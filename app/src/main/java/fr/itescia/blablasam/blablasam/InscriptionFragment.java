package fr.itescia.blablasam.blablasam;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Locale;

import fr.itescia.blablasam.bdd.Adresse;
import fr.itescia.blablasam.bdd.Inscription;
import fr.itescia.blablasam.bdd.MyDBHandler;
import fr.itescia.blablasam.bdd.Utilisateur;

/**
 * Gestion de la page d'inscription
 */
public class InscriptionFragment extends Fragment implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String LOG_TAG = "InscriptionFragment";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUND_PARIS = new LatLngBounds(
            new LatLng(48.856614, 2.0), new LatLng(49, 2.5));

    private Adresse adressePersonne = new Adresse();

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
        AutoCompleteTextView mAutocompleteTextView = (AutoCompleteTextView)rootView.findViewById(R.id.editTextAdresse);
        buttonValider.setOnClickListener(this);

         /* Google Auto complete API */
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(Places.GEO_DATA_API)
                        //.enableAutoManage(this.getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();
        mAutocompleteTextView = (AutoCompleteTextView)rootView.findViewById(R.id.editTextAdresse);
        mAutocompleteTextView.setThreshold(3);

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1,
                BOUND_PARIS, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

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



                try {
                    Utilisateur utilisateur = new Utilisateur(nom, prenom, dateDeNaissance, adressePersonne, login, pwd);
                    Inscription inscription = new Inscription(utilisateur,getActivity());
                    Thread thread_inscription = new Thread(inscription);
                    thread_inscription.start();



                } catch(Exception ex) {
                    System.out.println(ex.getMessage());
                }


                break;
            default:
                break;
        }
    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            //Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();


            String[] adress_element = place.getAddress().toString().split(",");

            if(place.getLocale().toString().equals(Locale.FRANCE.toString().toLowerCase())) {

                if(adress_element.length == 3)
                {
                    adressePersonne.setRue(adress_element[0]);
                    adressePersonne.setCodePostal(adress_element[1].trim().substring(0, 5));
                    adressePersonne.setVille(adress_element[1].replace(adressePersonne.getCodePostal(), ""));
                    adressePersonne.setPays(adress_element[2]);


                    System.out.println("Pays  : " + adressePersonne.getPays());
                    System.out.println("Ville  : " + adressePersonne.getVille());
                    System.out.println("Adresse  : " + adressePersonne.getRue());
                    System.out.println("CP  : " + adressePersonne.getCodePostal());
                }
                else
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Adresse incomplète ou invalide ",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
            else
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Internationalisation coming soon",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this.getActivity(),
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }
}
