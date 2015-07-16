package fr.itescia.blablasam.bdd;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import fr.itescia.blablasam.blablasam.SearchFragment;

public class SearchTrajet implements Runnable {
    private String departTrajet;
    private String destinationTrajet;
    private String dateTrajet;
    //private String heureDebut;
    private String heureFin;



    public SearchTrajet(String destinationTrajet, String dateTrajet,String heureFin, String departTrajet){
        this.destinationTrajet = destinationTrajet;
        this.dateTrajet = dateTrajet;
        this.heureFin = heureFin;
        this.departTrajet = departTrajet;
    }

    public SearchTrajet()
    {

    }

    @Override
    public void run() {
        try{

            //Supprime les caractères interdit dans l'URL
            this.destinationTrajet = this.destinationTrajet.replace(" ","");
            this.destinationTrajet = this.destinationTrajet.replace("''","");

            // Si la requete se déroule bien
            if(Server.sendGet("/SearchTrajet","" +
                    "destination="+this.destinationTrajet+
                    "&depart="+ this.departTrajet +
                    "&datefete="+this.dateTrajet+
                    "&heureFin="+this.heureFin) ==  "true" )
            {

            }
           /* InetAddress ip = InetAddress.getByName(Server.ServerIP);
            String user_password = this.operation + ";"+ this.lieuFete +";" + this.dateFete+ ";" + this.heureDebut +";" +this.heureFin;
            byte[] sendData = new byte[1024];

            sendData  = user_password.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,port);
            socket.send(sendPacket);
            System.out.println(operation +  " : envoyé");*/
        }
        catch (Exception ex ){
            System.out.println(ex.getMessage());
        }
    }

    public String getDepartTrajet() {
        return departTrajet;
    }

    public void setDepartTrajet(String departTrajet) {
        this.departTrajet = departTrajet;
    }

    public String getDestinationTrajet() {
        return destinationTrajet;
    }

    public void setDestinationTrajet(String destinationTrajet) {
        this.destinationTrajet = destinationTrajet;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getDateTrajet() {
        return dateTrajet;
    }

    public void setDateTrajet(String dateTrajet) {
        this.dateTrajet = dateTrajet;
    }
}
