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
            // Client will connect to this location
            site = new URL(InetAddress.getLocalHost().getHostAddress()+":8000/sendresults");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendRun(Run eList)
    {
        Gson g = new Gson();
        String json = g.toJson(eList);
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
            System.out.println("Run sent to server");


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
           Simulation.execute("ERROR","Run not sent to server.");
        }
    }
}
