package fr.itescia.blablasam.bdd;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SearchTrajet implements Runnable {
    private String lieuFete;
    private String dateFete;
    private String heureDebut;
    private String heureFin;



    public SearchTrajet(String lieuFete, String dateFete,String heureDebut,String heureFin){
        this.lieuFete = lieuFete;
        this.dateFete = dateFete;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;

    }

    @Override
    public void run() {
        try{

            //Supprime les caractères interdit dans l'URL
            this.lieuFete = this.lieuFete.replace(" ","");
            this.lieuFete = this.lieuFete.replace("''","");

            // Si la requete se déroule bien
            if(Server.sendGet("/trajet","" +
                    "lieu="+this.lieuFete+
                    "&datefete="+this.dateFete+
                    "&heureDebut="+ this.heureDebut+
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
}
