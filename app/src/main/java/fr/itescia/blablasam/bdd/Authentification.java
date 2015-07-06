package fr.itescia.blablasam.bdd;

import android.app.Activity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by William- on 04/07/2015.
 */
public class Authentification implements Runnable {
    private String login;
    private String password;
    private  DatagramSocket socket;
    private final int port = 8888;
    private final String operation = "CONNEXION";


    public Authentification(String login, String password, DatagramSocket socket)
    {
        this.login = login;
        this.password = password;
        this.socket = socket;

    }

    @Override
    public void run() {

        try
        {

            InetAddress ip = InetAddress.getByName("192.168.10.21");
            String user_password = this.operation +";" + this.login+ ";" + this.password;
            byte[] sendData = new byte[1024];


            sendData  = user_password.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,port);
            socket.send(sendPacket);
            System.out.println(operation +  " : envoyé");


        }
        catch (Exception ex )
        {
            System.out.println("Err  : "+ operation +" " + ex.getMessage());
        }

    }
}
