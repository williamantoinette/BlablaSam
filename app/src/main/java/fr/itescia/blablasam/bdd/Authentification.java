package fr.itescia.blablasam.bdd;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import com.google.gson.*;

import fr.itescia.blablasam.blablasam.ConnexionFragment;
import fr.itescia.blablasam.blablasam.R;
import fr.itescia.blablasam.blablasam.SearchActivity;
import fr.itescia.blablasam.exception.ServerException;

public class Authentification implements Runnable {
    private String login;
    private String password;
    private Activity activity;


    public Authentification(String login, String password, Activity activity){
        this.login = login;
        this.password = password;
        this.activity =activity;

    }

    @Override
    public void run() {
        try{


            if(Server.sendGet("/authentification","login="+this.login +"&password=" + this.password).equals("true"))
            {

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Connexion OK ", Toast.LENGTH_LONG).show();
                            Fragment fragment = new SearchActivity();

                            if(fragment!=null) {
                                FragmentManager fragmentManager = activity.getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                            }
                        }
                    });

            }
            else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Echec d'authentification", Toast.LENGTH_LONG).show();
                    }
                });

            }
         /*   InetAddress ip = InetAddress.getByName(Server.ServerIP);
            String user_password = this.operation +";" + this.login+ ";" + this.password;
            byte[] sendData = new byte[1024]9;

            sendData  = user_password.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,port);
            socket.send(sendPacket);
            System.out.println(operation +  " : envoyé");*/
        }
        catch (Exception ex ){


                System.out.println("Err" + ex.getMessage());
                activity.runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               Toast.makeText(activity, "Impossible de joindre le serveur", Toast.LENGTH_LONG).show();
                                           }
                                       }
                );



        }

    }
}
