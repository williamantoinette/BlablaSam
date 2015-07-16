package fr.itescia.blablasam.bdd;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.itescia.blablasam.exception.ServerException;


/**
 * Created by William- on 06/07/2015.
 */
public class Server {
    private static final String USER_AGENT = "Mozilla/5.0";
    public static String ServerIP = "http://192.168.1.13:7745/AndroidServer";
    public static String sendGet(String uri,String parameters) throws Exception {


        String url = Server.ServerIP + "/" + uri +"?"+ parameters ;

        System.out.println(url);

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setConnectTimeout(3000);


        con.setRequestMethod("GET");


        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        System.out.println("Message retour : "+  con.getResponseMessage());
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        if(responseCode != 200)
        {
            throw new ServerException();
        }

        return response.toString();

    }
    public static String sendJSONPost(String uri, JsonElement jsonObject)
    {
        try {


            //constants
            URL url = new URL(Server.ServerIP + "/" + uri);
            String message =  jsonObject.toString();

            System.out.println("Envoi d'un post à : " +url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(4000);
            conn.setConnectTimeout(4000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(message.getBytes().length);

            //make some HTTP header nicety
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            //open
            conn.connect();

            //setup send
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(message.getBytes());
            //clean up
            os.flush();

            System.out.println("Retour : " +conn.getResponseMessage());
            return conn.getResponseMessage();
        }
        catch (Exception ex) {
            System.out.println("Err: " + ex);
        }

        return  null;
    }





}
