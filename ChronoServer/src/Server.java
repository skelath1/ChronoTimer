
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;


public class Server{
        // a shared area where we get the POST data and then use it in the other handler
        static String sharedResponse = "";
        static String command = "";

        static String value = null;
        static Run theRuns = new Run();
        static Members mem = new Members("ChronoServer/src/racers.txt");

	public static void main(String[] args) throws Exception {

            // set up a simple HTTP server on our local host
            HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),8000), 0);
			//add members from racers.txt

            // create a context to get the request for the POST
            server.createContext("/sendresults",new PostHandler());
            server.createContext("/results", new DirectoryHandler());
            server.createContext("/results/style.css", new StyleHandler());
            server.createContext("/results/index.js", new ScriptHandler());
            //create a context to display employees
            server.setExecutor(null); // creates a default executor
            // get it going
            System.out.println("Server Online.");
            server.start();
			System.out.println("addresss: " +InetAddress.getLocalHost().getHostAddress() + ":8000/results");
        }

         static class PostHandler implements HttpHandler {
            public void handle(HttpExchange transmission) throws IOException {
                if(theRuns != null)
                    theRuns.clear();


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

                String postResponse = "ERROR";

                ///DOnt need this here
                //parse sharedResponse and handle with Editor class
                String[] parts = sharedResponse.split(" ", 2);
                if(parts.length == 1){
                    command = parts[0];
                    value = null;
                    if(command.equals("clear")){
						theRuns.clear();
                    }
                }else{
                    //2 parts
                    postResponse = "JSON HAS BEEN RECEIVED";
                    command = parts[0];
                    value = parts[1];

                    //add json
                    Gson gson = new Gson();
                    try{
                        theRuns = gson.fromJson(value, Run.class);
                    }catch(JsonSyntaxException ex){
                        System.out.println("Error in syntax occured.");
                    }

					theRuns.printResults();
                }

                transmission.sendResponseHeaders(300, postResponse.length());
                outputStream = transmission.getResponseBody();
                outputStream.write(postResponse.getBytes());
                outputStream.close();
            }
        }
        static class DirectoryHandler implements HttpHandler{
            public void handle(HttpExchange t) throws IOException{

                //add the event type to the html string with javascript
                String response = "";
                ArrayList<Result> db = theRuns.getResults();
                response += readContents("src/index.txt");
                int count = 1;
                for(Result r: db){
                	String name =mem.getName(r.get_bib());
                	if(name !=null){
						response += "<tr> <th>" + count +"</th>" +
							"<th>" + r.get_bib() +"</th>"+
							"<th>" + name +"</th>" +
							"<th>" + r.get_time() +"</th></tr>";
					}else{
						response += "<tr> <th>" + count +"</th>" +
							"<th>" + r.get_bib() +"</th>"+
							"<th>" + "</th>" +
							"<th>" + r.get_time() +"</th></tr>";
					}
                    ++count;
                }
                String eventName = "Race Type: " + theRuns.getEventType();
                if(theRuns.getEventType() == null){
                    eventName = "";
                }

                response += " </tbody></table>" +"<script>\n" +
                        "\tdocument.getElementById(\"event\").innerHTML = \""+ eventName+"\";\n" +
                        "</script>"+
                        "</div><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>" +
                        "<script src=\"results/index.js\"></script>" +
					    " <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>" +
                        "</body>" +
                        "</html>";

                //write
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.flush();
                os.close();
            }
        }

        static class StyleHandler implements HttpHandler{
            public void handle(HttpExchange t) throws IOException{
                String response = "";
                //System.out.println("css handler working...");
                try(Scanner fr = new Scanner(new File("src/css/style.css"))){
                    while(fr.hasNextLine()){
                        response += fr.nextLine() + "\n";
                    }
                }
               // System.out.println(response);
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.flush();
                os.close();
            }
        }
        static class ScriptHandler implements HttpHandler{
            public void handle(HttpExchange t) throws IOException{
                String response = "";
                //System.out.println("css handler working...");
                try(Scanner fr = new Scanner(new File("src/js/index.js"))){
                    while(fr.hasNextLine()){
                        response += fr.nextLine() + "\n";
                    }
                }
                // System.out.println(response);
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
                System.out.println("Couldn't get file: " + filename);
            }
            return responseBuilder.toString();
        }

    }


