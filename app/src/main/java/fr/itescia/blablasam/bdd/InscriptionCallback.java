/*
package fr.itescia.blablasam.bdd;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.Toast;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import fr.itescia.blablasam.blablasam.ConnexionFragment;
import fr.itescia.blablasam.blablasam.R;

public class InscriptionCallback implements Runnable {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private boolean estRecu = false;
    private Activity activity;

    public InscriptionCallback(Activity activity, DatagramSocket socket) {
        try {
            this.socket = socket;
            this.estRecu = false;
            this.activity = activity;
        } catch (Exception ex) {
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

                if(status_connexion.trim().equals("SUCCESS")) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,"Inscription OK ",Toast.LENGTH_LONG).show();
                            Fragment fragment = new ConnexionFragment();
                            if(fragment!=null) {
                                FragmentManager fragmentManager = activity.getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                            }
                        }
                    });
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Echec de l'inscription", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        } catch (Exception ex) {
            System.out.println("Err " +ex.getMessage());
        }
    }
}
*/
