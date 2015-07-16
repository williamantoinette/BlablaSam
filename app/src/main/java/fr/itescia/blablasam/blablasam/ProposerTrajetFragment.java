package fr.itescia.blablasam.blablasam;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Intent;
import android.provider.CalendarContract;
import android.text.InputType;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.List;

import fr.itescia.blablasam.bdd.Adresse;
import fr.itescia.blablasam.bdd.GPS;
import fr.itescia.blablasam.bdd.Server;
import fr.itescia.blablasam.bdd.Trajet;
import fr.itescia.blablasam.bdd.Utilisateur;

/**
 * Gestion de la proposition des trajets
 */
public class ProposerTrajetFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    private EditText editTextHeureDepart;
    private TimePickerDialog timePickerDialog;

    private EditText editTextDate;
    private EditText editTextDestination;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    //region Champs Autocomplétion
    private AutoCompleteTextView mAutocompleteDepart;
    private AutoCompleteTextView mAutocompleteDestination;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUND_PARIS = new LatLngBounds(new LatLng(48.856614, 2.0), new LatLng(49, 2.5));

    private static final String LOG_TAG = "ProposerTrajetFragment";
    private static final int GOOGLE_API_CLIENT_ID = 1;
    //endregion

    private Utilisateur currentUser;
    private Trajet trajet = new Trajet();

    //Champs de saisie
    private EditText dateTrajet;
    private EditText nbPassager;
    private EditText detourMax;

    private ImageButton locationDepart;
    private ImageButton locationArrive;
    private GPS gps;
    private Location maPosition;

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

        gps = new GPS(this.getActivity());

        dateTrajet = (EditText)rootView.findViewById(R.id.dateTrajet);
        nbPassager = (EditText)rootView.findViewById(R.id.nbPassager);
        detourMax = (EditText)rootView.findViewById(R.id.editDetour);
        locationDepart = (ImageButton)rootView.findViewById(R.id.locationDepart);
        locationArrive = (ImageButton)rootView.findViewById(R.id.locationArrive);

        locationDepart.setOnClickListener(this);
        locationArrive.setOnClickListener(this);

        //region AUTOCOMPLETION partie 1
         /* Google Auto complete API */
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(Places.GEO_DATA_API)
                        //.enableAutoManage(this.getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();


            //Auto complétion sur le champ départ
            mAutocompleteDepart = (AutoCompleteTextView) rootView.findViewById(R.id.editTextDepart);

            mAutocompleteDepart.setThreshold(3);

            mAutocompleteDepart.setOnItemClickListener(mAutocompleteClickListener);
            mPlaceArrayAdapter = new PlaceArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1,
                    BOUND_PARIS, null);
            mAutocompleteDepart.setAdapter(mPlaceArrayAdapter);


        // Auto complétion sur le champ arrivée
        mAutocompleteDestination = (AutoCompleteTextView) rootView.findViewById(R.id.editTextDestination);
        mAutocompleteDestination.setThreshold(3);

        mAutocompleteDestination.setOnItemClickListener(mAutocompleteClickListener);
        mAutocompleteDestination.setAdapter(mPlaceArrayAdapter);


        /* Fin auto complete API */

        //endregion PARTI PARTIE i
        currentUser = Utilisateur.getCurrentUser();
        mAutocompleteDestination.setText(currentUser.getAdresse().getRue() + "," +
                currentUser.getAdresse().getVille() + "," +
                currentUser.getAdresse().getPays());

        trajet.setConducteur(currentUser);
        trajet.setDestination(currentUser.getAdresse());
        Button buttonValider = (Button)rootView.findViewById(R.id.buttonValider);
        buttonValider.setOnClickListener(this);

        editTextDestination = (EditText)rootView.findViewById(R.id.editTextDestination);

        editTextDate = (EditText)rootView.findViewById(R.id.editTextDate);
        editTextDate.setInputType(InputType.TYPE_NULL);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        editTextHeureDepart = (EditText) rootView.findViewById(R.id.editTextHeureDepart);
        editTextHeureDepart.setInputType(InputType.TYPE_NULL);

        setDateTimeField();

        return rootView;
    }

    /**
     * Gestion des buttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {

                case R.id.editTextDate:
                    datePickerDialog.show();
                    break;

                case R.id.editTextHeureDepart:
                    timePickerDialog.show();
                    break;

                case R.id.buttonValider:
                    Adresse depart = new Adresse();
                    // On affecte l'adresse de départ
                    String[] adr_elem = mAutocompleteDepart.getText().toString().split(",");
                    switch (adr_elem.length) {
                        case 3:
                            depart.setRue(adr_elem[0]);
                            depart.setVille(adr_elem[1]);
                            depart.setPays(adr_elem[2]);
                            break;
                        
                        case 4:
                            depart.setRue(adr_elem[1]);
                            depart.setVille(adr_elem[2]);
                            depart.setPays(adr_elem[3]);
                            break;
                        
                        default:
                            break;
                    }

                    trajet.setDepart(depart);
                    trajet.setNombrePlace(Integer.parseInt(nbPassager.getText().toString()));
                    trajet.setDetourMax(Integer.parseInt(detourMax.getText().toString()));

                    Toast.makeText(getActivity(), "Création du trajet ok", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new MesTrajetsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

                    Toast.makeText(getActivity(), "Création d'un événement dans l'agenda", Toast.LENGTH_SHORT).show();
                    valider();

                    break;

                case R.id.locationDepart:
                    if (gps.canGetLocation()) {
                        maPosition = gps.getLocation();
                        Geocoder geo = new Geocoder(this.getActivity(), Locale.getDefault());
                        List<Address> addresses =
                                geo.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                        mAutocompleteDepart.setText(addresses.get(0).getAddressLine(0) +"," + addresses.get(0).getLocality()+ "," + addresses.get(0).getCountryName());
                        // maPosition.getExtras().
                        //mAutocompleteDepart.setText(gps.getLocation());
                    } else {
                        gps.showSettingsAlert();
                    }
                    break;

                case R.id.locationArrive:
                    if (gps.canGetLocation()) {
                        maPosition = gps.getLocation();
                        Geocoder geo = new Geocoder(this.getActivity(), Locale.getDefault());
                        List<Address> addresses =
                                geo.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                        mAutocompleteDestination.setText(addresses.get(0).getAddressLine(0) +"," + addresses.get(0).getLocality()+ "," + addresses.get(0).getCountryName());
                        // maPosition.getExtras().
                        //mAutocompleteDepart.setText(gps.getLocation());
                    } else {
                        gps.showSettingsAlert();
                    }
                    break;
                
                default:
                    break;
            }

        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        Thread thread_trajet = new Thread(new Runnable() {
            @Override
            public void run() {
                Gson serialiseur = new Gson();
                JsonElement JSONTrajet = serialiseur.toJsonTree(trajet);

                if(Server.sendJSONPost("/trajet",JSONTrajet) == "true"){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Trajet proposé avec succès", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        thread_trajet.start();
    }



    //region AUTOCOMPLETION partie 2
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
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
                        Toast.makeText(getActivity(), "Internationalisation coming soon", Toast.LENGTH_SHORT).show();
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
        Log.e(LOG_TAG, "Google Places API connection failed with error code: " + connectionResult.getErrorCode());
        Toast.makeText(this.getActivity(), "Google Places API connection failed with error code:" + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }
    //endregion


    /**
     * Validation du formulaire
     */
    public void valider(){
//        https://github.com/vogellacompany/codeexamples-android/blob/680a4068a9c9e4b6258c4d88d7d105ca51c8df81/de.vogella.android.calendarapi/src/de/vogella/android/calendarapi/MyCalendarActivity.java
        // Récupération des saisies
        String[] arrayEditTextDate = editTextDate.getText().toString().split("/");
        int day = Integer.parseInt(arrayEditTextDate[0]);
        int month = Integer.parseInt(arrayEditTextDate[1]);
        int year = Integer.parseInt(arrayEditTextDate[2]);
        String destination = editTextDestination.getText().toString();
        String title = "Trajet BlablaSam !";
        String description = "Ne pas oublier vos co-samer";

        // Création d'un évenement dans l'agenda
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, destination);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);

        // Ajout de la date
        long debut = convertHeureMinutes(editTextHeureDepart.getText().toString());
        long fin = debut + 1800000;
        GregorianCalendar calDate = new GregorianCalendar(year, month-1, day);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis() + debut);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis() + fin);

        // Accès : public / Disponibilité : occupé
        intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PUBLIC);
        intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

//        // Gestion des notifications
//        http://developer.android.com/reference/android/provider/CalendarContract.html
//        http://developer.android.com/reference/android/provider/CalendarContract.Reminders.html
//        intent.putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
//        intent.putExtra(CalendarContract.Reminders.MINUTES, 10);

        startActivity(intent);
    }

    /**
     * Convertion d'une chaine de caractère HH:MM en minutes (int)
     * @param strHours
     * @return
     */
    public long convertHeureMinutes(String strHours){
        String[] splitHours = strHours.split(":");
        int minutes = (Integer.parseInt(splitHours[0]) * 60) + Integer.parseInt(splitHours[1]);
        return TimeUnit.MINUTES.toMillis(minutes);
    }

    /**
     * Gestion du DatPicker
     */
    private void setDateTimeField() {
        try{
            editTextDate.setOnClickListener(this);
            editTextHeureDepart.setOnClickListener(this);
            Calendar newCalendar = Calendar.getInstance();

            datePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    editTextDate.setText(simpleDateFormat.format(newDate.getTime()));
                }
            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            // Time picker pour l'heure de départ
            timePickerDialog = new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    editTextHeureDepart.setText(hourOfDay + ":" + minute);
                }
            }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

}
