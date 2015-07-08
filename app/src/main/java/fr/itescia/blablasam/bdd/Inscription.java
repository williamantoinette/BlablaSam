package fr.itescia.blablasam.bdd;

public class Inscription implements Runnable {
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String adresse;
    private String DateNaissance;



    public Inscription(Utilisateur utilisateur){
        this.login = utilisateur.getLogin();
        this.password = utilisateur.getPassword();
        this.nom = utilisateur.getNom();
        this.prenom = utilisateur.getPrenom();
        this.DateNaissance = utilisateur.getDateDeNaissance();

    }

    @Override
    public void run() {

        try
        {
            if(Server.sendGet("/inscription",
                                     "login="+this.login +
                                    "&password=" + this.password +
                                    "&nom=" + this.nom +
                                    "&prenom=" + this.nom +
                                    "&adresse=" + this.adresse.replace(" ","_") +
                                    "&DateNaissance=test") == "true")
            {

                //Inscription OK

            }
            else
            {
                // Inscription failed
            }
        }
        catch (Exception ex )
        {
            System.out.println(ex.getMessage());
        }



      /*  try{
            InetAddress ip = InetAddress.getByName(Server.ServerIP);
            String user_password = this.operation +";"+ this.nom +";"+ this.prenom +";" + this.adresse +";" + this.DateNaissance +";" + this.login+ ";" + this.password;
            byte[] sendData = new byte[1024];

            sendData  = user_password.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip,port);
            socket.send(sendPacket);
            System.out.println(operation +  " : envoyé");
        } catch (Exception ex ) {
            System.out.println("Err  : "+ operation +" " + ex.getMessage());
        }*/
    }
}

