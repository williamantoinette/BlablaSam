package fr.itescia.blablasam.blablasam;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by William- on 06/07/2015.
 */
public class MapFragment extends Activity {

    private GoogleApiClient googlePlace;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try{
            googlePlace = new GoogleApiClient
                    .Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)


                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            System.out.println("Connexion API OK");
                            System.out.println("Test connexion : " + googlePlace.isConnected());

                            Thread autoComplete = new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    System.out.println("Test connexion 2 : " + googlePlace.isConnected());
                                    LatLng latitude = new LatLng(45, 1);
                                    LatLng longitude = new LatLng(50, 5);


                                    PendingResult<AutocompletePredictionBuffer> result = (Places.GeoDataApi.getAutocompletePredictions(googlePlace, "https://maps.googleapis.com/maps/api/place/queryautocomplete/json?key=AIzaSyAbjNu9IQGy_eFaNwnhoHfHjKLV1xJ2Y08&language=fr&input=pizza+near%20par",
                                            new LatLngBounds(latitude, longitude), null));


                                    AutocompletePredictionBuffer autocompletePredictions = result.await(60, TimeUnit.SECONDS);
                                    try {
                                        //Thread.sleep(5000);
                                    } catch (Exception ex) {

                                    }
                                    System.out.println(autocompletePredictions.getStatus());
                                    System.out.println(autocompletePredictions.getCount());
                                    Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
                                    while (iterator.hasNext()) {
                                        AutocompletePrediction prediction = iterator.next();

                                        System.out.println(prediction.getDescription());


                                    }


                                }
                            });

                            autoComplete.start();
                        }

                        @Override
                        public void onConnectionSuspended(int i) {

                            System.out.println("Connexion API OFF");
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                        }
                    })
                    .build();




            googlePlace.connect();

            int PLACE_PICKER_REQUEST = 1;
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            Context context = getApplicationContext();
            try {
                //startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
            }
            catch (Exception ex)
            {
                System.out.println("E0 :" + ex.getMessage());
            }

        }
        catch (Exception ex){

            System.out.println("E1 :" + ex.getMessage());
        }



    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        googlePlace = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                        System.out.println("API OK");
                        int PLACE_PICKER_REQUEST = 1;
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                        Context context = getApplicationContext();
                        //startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .build();

        googlePlace.connect();

        return rootView;
    }
*/

}
