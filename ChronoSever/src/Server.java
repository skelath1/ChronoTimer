import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class Server{
        // a shared area where we get the POST data and then use it in the other handler
        static String sharedResponse = "";
        static String command = "";
        static String value = null;
        public static void main(String[] args) throws Exception {

            // set up a simple HTTP server on our local host
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // create a context to get the request for the POST
            server.createContext("/sendresults",new PostHandler());
            server.createContext("/displayresults/directory", new DirectoryHandler());
            //create a context to display employees
            server.setExecutor(null); // creates a default executor
            // get it going
            System.out.println("Starting Server...");
            server.start();

        }

        static class PostHandler implements HttpHandler {
            public void handle(HttpExchange transmission) throws IOException {

                //  shared data that is used with other handlers
                sharedResponse = "";

                // set up a stream to read the body of the request
                InputStream inputStr = transmission.getRequestBody();

                // set up a stream to write out the body of the response
                OutputStream outputStream = transmission.getResponseBody();

                // string to hold the result of reading in the request
                StringBuilder sb = new StringBuilder();

                // read the characters from the request byte by byte and build up the sharedResponse
                int nextChar = inputStr.read();
                while (nextChar > -1) {
                    sb=sb.append((char)nextChar);
                    nextChar=inputStr.read();
                }

                // create our response String to use in other handler
                sharedResponse = sharedResponse+sb.toString();

                // respond to the POST with ROGER




                //Desktop dt = Desktop.getDesktop();
                //dt.open(new File("raceresults.html"));

                // assume that stuff works all the time


                String postResponse = "ERROR";

                ///DOnt need this here
                //parse sharedResponse and handle with Editor class
                String[] parts = sharedResponse.split(" ", 2);
                if(parts.length == 1){
                    command = parts[0];
                    value = null;
                    System.out.println("command " + command);
                    //print or clear
                    if(command.equals("PRINT")) {
                        //postResponse = editor.print();
                    }
                    else if(command.equals("CLEAR"))
                        postResponse = "List has been cleared.";
                    //editor.clear();
                }else{
                    //2 parts
                    postResponse = "JSON HAS BEEN RECEIVED";
                    command = parts[0];
                    value = parts[1];
                    //add json
                    System.out.println("Value: " + value);
                    //editor.add(value);
                }

                transmission.sendResponseHeaders(300, postResponse.length());
                OutputStream os = transmission.getResponseBody();
                os.write(postResponse.getBytes());
                os.close();


            }
        }
        static class DirectoryHandler implements HttpHandler{
            public void handle(HttpExchange t) throws IOException{
                System.out.println("in Director handler");
                //todo:

                String response = "";
                //ArrayList<Employee> db = editor.sendData();
                response += readContents("src/index.txt");
                /*for(Employee e: db){
                    response += "<tr> <th>" + e.getTitle() +"</th>" +
                            "<th>" + e.getFirstName() +"</th>"+
                            "<th>" + e.getLastName() +"</th>" +
                            "<th>" + e.getDepartment() +"</th>"+
                            "<th>" + e.getPhoneNumber() +"</th>" +
                            "<th>" + e.getGender() +"</th> </tr>";
                }*/
                response += " </table>\n" +
                        "</body>\n" +
                        "</html>";
                //write database content to web page
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.flush();
                os.close();
            }
        }


        static String readContents(String filename){
            StringBuilder responseBuilder = new StringBuilder();
            try {
                BufferedReader in = new BufferedReader(new FileReader(filename));
                String str;
                while ((str = in.readLine()) != null) {
                    responseBuilder.append(str);
                }
                in.close();
            } catch (IOException e) {
                System.out.println("couln't get file");
            }
            return responseBuilder.toString();
        }

    }


