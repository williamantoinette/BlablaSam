package fr.itescia.blablasam.bdd;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import fr.itescia.blablasam.blablasam.MainActivity;
import fr.itescia.blablasam.blablasam.R;
import fr.itescia.blablasam.blablasam.SearchActivity;

/**
 * Created by William- on 04/07/2015.
 */
public class AuthentificationCallback implements Runnable {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private boolean estRecu = false;
    private Activity activity;

    public AuthentificationCallback(Activity activity, DatagramSocket socket) {

        try {
            this.socket = socket;
            this.estRecu = false;
            this.activity = activity;

        }
        catch (Exception ex) {
            System.out.println("Err : " +ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {

            while (!estRecu) {

                byte[] donnee = new byte[1024];

                packet = new DatagramPacket(donnee, donnee.length);
                socket.receive(packet);
                String status_connexion = new String(packet.getData());
                estRecu = true;
                System.out.println("Recu");

                if(status_connexion.trim().equals("connected"))
                {

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,"Connexion OK ",Toast.LENGTH_LONG).show();
                            Fragment fragment = new SearchActivity();
                            if(fragment!=null) {
                                FragmentManager fragmentManager = activity.getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                            }
                        }
                    });


            }
            else
                {


                   activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Echec d'authentification", Toast.LENGTH_LONG).show();
                        }
                    });

                }


            }
        }
        catch (Exception ex) {

            System.out.println("Err " +ex.getMessage());
        }
    }
}
