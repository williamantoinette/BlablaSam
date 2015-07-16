package  fr.itescia.blablasam.blablasam;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.itescia.blablasam.bdd.SearchTrajet;

public class SearchFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, View.OnClickListener {

    //TODO Renommer la classe en SearchFragment
    //Gestion de la date du trajet
    private DatePickerDialog dateTrajet;
    private SimpleDateFormat dateFormatter;
    private EditText dateTxtEdit;
    private SimpleDateFormat heureFormatter;

    private TimePickerDialog heureDebut;
    private EditText heureDebutTXT;


    private TimePickerDialog heureFin;
    private EditText heureFinTXT;

    private static final String LOG_TAG = "SearchActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;

    //Auto complétion lieu de départ
    private AutoCompleteTextView mAutocompleteTextView;
    //Auto complétion lieu d'arrivée
    private AutoCompleteTextView  mAutocompleteArrive;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUND_PARIS = new LatLngBounds(
            new LatLng(48.856614, 2.0), new LatLng(49, 2.5));

    private EditText lieuFete;
    private EditText dateFete;
    //private EditText heureDebut;
    //private EditText heureFin;
    private EditText villeArrive;
    private Button btnRecherche;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_search, container, false);


        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        heureFormatter = new SimpleDateFormat("hh:mm", Locale.FRANCE);

        //region Autocomplétion
        /* Google Auto complete API */
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(Places.GEO_DATA_API)
                        //.enableAutoManage(this.getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();

        //Auto complétion
        mAutocompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.LieuTrajet);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

        mAutocompleteArrive = (AutoCompleteTextView)rootView.findViewById(R.id.villeArrive);
        mAutocompleteArrive.setThreshold(3);
        mAutocompleteArrive.setOnItemClickListener(mAutocompleteClickListener);




        mPlaceArrayAdapter = new PlaceArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1,
                BOUND_PARIS, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
        mAutocompleteArrive.setAdapter(mPlaceArrayAdapter);


        /* Fin auto complete API */
        //endregion


       // lieuFete = (EditText) rootView.findViewById(R.id.LieuTrajet);

        dateTxtEdit = (EditText) rootView.findViewById(R.id.dateTrajet);
        dateTxtEdit.setInputType(InputType.TYPE_NULL);

        heureDebutTXT = (EditText) rootView.findViewById(R.id.HeureDebut);
        heureDebutTXT.setInputType(InputType.TYPE_NULL);

        heureFinTXT = (EditText) rootView.findViewById(R.id.HeureDebut);
        heureFinTXT.setInputType(InputType.TYPE_NULL);

        villeArrive = (EditText) rootView.findViewById(R.id.villeArrive);

        btnRecherche = (Button)rootView.findViewById(R.id.btnRecherche);
        btnRecherche.setOnClickListener(this);

        setDateTimeField();


        return rootView;
    }

    //region autocomplétion partie 2
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
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
                for (String e : adress_element) {
                    System.out.println(e);
                }
            }
            else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Internationalisation coming soon",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    //endregion
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

    //Transform EditText to DatePicker
    private void setDateTimeField() {
        dateTxtEdit.setOnClickListener(this);
        heureFinTXT.setOnClickListener(this);
        heureDebutTXT.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        dateTrajet = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateTxtEdit.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        //Time picker pour Heure debut
        heureDebut = new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                heureDebutTXT.setText(hourOfDay + minute);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);


        //Time picker pour Heure fin
        heureFin = new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                heureFinTXT.setText(hourOfDay + ":" + minute);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dateTrajet) {
            dateTrajet.show();

        }
        if (v.getId() == R.id.HeureDebut) {
            //heureDebut.show();
        }

        if (v.getId() == R.id.HeureDebut) {
            heureFin.show();
        }
        if (v.getId() == R.id.btnRecherche) {
            try
            {
                String villeDepart = "";
                String villeArrivee = "";

                String [] depart_element = mAutocompleteTextView.getText().toString().split(",");
                String [] arrive_element = mAutocompleteArrive.getText().toString().split(",");

                //region recuperation ville départ
                switch (depart_element.length)
                {
                    case 3 :
                        villeDepart = depart_element[1].trim();
                        break;
                    case 4 :
                        villeDepart = depart_element[2].trim();
                        break;
                    default:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"Adresse incorrecte",Toast.LENGTH_SHORT).show();
                            }
                        });

                }
                //endregion

                //region recuperation ville arrive
                switch (arrive_element.length)
                {
                    case 3 :
                        villeArrivee = arrive_element[1].trim();
                        break;
                    case 4 :
                        villeArrivee = arrive_element[2].trim();
                        break;
                    default:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"Adresse incorrecte",Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                }

                //endregion
                System.out.println("Recherche d'un trajet");
                SearchTrajet trajet = new SearchTrajet();

                trajet.setDepartTrajet(villeDepart);
                trajet.setDestinationTrajet(villeArrivee);
                trajet.setDateTrajet(dateTxtEdit.getText().toString());
                trajet.setHeureFin(heureDebutTXT.getText().toString());


                Thread thread_search = new Thread(trajet);
                thread_search.start();
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

        }
    }
}