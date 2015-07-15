package fr.itescia.blablasam.blablasam;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Gestion de la proposition des trajets
 */
public class ProposerTrajetFragment extends Fragment implements View.OnClickListener{

    private EditText editTextDate;
    private EditText editTextDestination;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

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

        Button buttonValider = (Button)rootView.findViewById(R.id.buttonValider);
        buttonValider.setOnClickListener(this);

        editTextDestination = (EditText)rootView.findViewById(R.id.editTextDestination);

        editTextDate = (EditText)rootView.findViewById(R.id.editTextDate);
        editTextDate.setInputType(InputType.TYPE_NULL);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        setDateTimeField();

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
                this.valider();
                break;

            case R.id.editTextDate:
                datePickerDialog.show();

            default:
                break;
        }

    }


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
        String title = "Mon premier trajet";
        String description = "Ne pas oublier vos co-samer";

        // Création d'un évenement dans l'agenda
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, destination);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);

        // Ajout de la date
        long debut = convertHeureMinutes("01:30");
        long fin = convertHeureMinutes("10:58");
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
        editTextDate.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextDate.setText(simpleDateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
}
