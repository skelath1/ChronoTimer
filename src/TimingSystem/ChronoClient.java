package TimingSystem;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

public class ChronoClient {
    String content = "";
    URL site;
    HttpURLConnection conn;


//
    public ChronoClient(){
        start();
    }

    private void start() {

        try {
            site = new URL("http://" + InetAddress.getLocalHost().getHostAddress()+":8000/sendresults");

        } catch (Exception e) {
            e.printStackTrace();
            Simulation.execute("ERROR","CANNOT CREATE URL");
            e.printStackTrace();
        }
    }
    public void sendRun(Run runList)
    {
        Gson g = new Gson();
        String json = g.toJson(runList.getResults());

        content = "ADD " + json;
        try {

            conn = (HttpURLConnection) site.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());


            if (content != null) {
                out.writeBytes(content);
                out.flush();
                out.close();
            }

            InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

            // string to hold the result of reading in the response
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up
            int nextChar;
            while ((nextChar = inputStr.read()) > -1) {
                sb = sb.append((char) nextChar);
            }
            //System.out.println("Return String: " + sb);
        }catch(Exception e){
            e.printStackTrace();
            Simulation.execute("ERROR","COULD NOT SEND RUN TO SERVER");
        }
    }
    /*public void startRun(){
        String post = "clear";
        try {

            conn = (HttpURLConnection) site.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());


            if (content != null) {
                out.writeBytes(content);
                out.flush();
                out.close();
            }
            System.out.println("clear sent to server");


            InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

            // string to hold the result of reading in the response
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up
            int nextChar;
            while ((nextChar = inputStr.read()) > -1) {
                sb = sb.append((char) nextChar);
            }
            //System.out.println("Return String: " + sb);
        }catch(Exception e){
            Simulation.execute("ERROR","COULD NOT SEND RUN TO SERVER");
            e.printStackTrace();
        }
    }*/
}
