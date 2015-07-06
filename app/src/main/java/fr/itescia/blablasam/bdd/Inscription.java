package fr.itescia.blablasam.bdd;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Inscription implements Runnable {
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String adresse;
    private String DateNaissance;
    private DatagramSocket socket;
    private final int port = 8888;
    private final String operation = "INSCRIPTION";


    public Inscription(String nom, String prenom, String adresse, String dateNaissance,String login, String password, DatagramSocket socket){
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.DateNaissance = dateNaissance;
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            InetAddress ip = InetAddress.getByName(Server.ServerIP);
            String user_password = this.operation +";"+ this.nom +";"+ this.prenom +";" + this.adresse +";" + this.DateNaissance +";" + this.login+ ";" + this.password;
            byte[] sendData = new byte[1024];

            sendData  = user_password.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,port);
            socket.send(sendPacket);
            System.out.println(operation +  " : envoyé");
        } catch (Exception ex ) {
            System.out.println("Err  : "+ operation +" " + ex.getMessage());
        }
    }
}

