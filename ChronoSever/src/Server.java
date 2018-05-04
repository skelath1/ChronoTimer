
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Scanner;


public class Server{
        // a shared area where we get the POST data and then use it in the other handler
        static String sharedResponse = "";
        static String command = "";

        static String value = null;
        static Run theRuns = new Run();
        static Members mem = new Members("ChronoSever/src/racers.txt");

	public static void main(String[] args) throws Exception {

            // set up a simple HTTP server on our local host
            HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),8000), 0);
			//add members from racers.txt

            // create a context to get the request for the POST
            server.createContext("/sendresults",new PostHandler());
            server.createContext("/results", new DirectoryHandler());
            server.createContext("/results/style.css", new StyleHandler());
            //create a context to display employees
            server.setExecutor(null); // creates a default executor
            // get it going
            System.out.println("Starting Server...");
            server.start();
			System.out.println("addresss: " +InetAddress.getLocalHost().getHostAddress());
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

                String postResponse = "ERROR";

                ///DOnt need this here
                //parse sharedResponse and handle with Editor class
                String[] parts = sharedResponse.split(" ", 2);
                if(parts.length == 1){
                    command = parts[0];
                    value = null;
                    System.out.println("command " + command);
                    if(command.equals("clear")){
						theRuns.clear();
                    }
                }else{
                    //2 parts
                    postResponse = "JSON HAS BEEN RECEIVED";
                    command = parts[0];
                    value = parts[1];
                    //add json
					theRuns.add(value);

					//add the run to the list of runs to use so it can be viewed later
                    System.out.println("Value: " + value);
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
                System.out.println("in Director handler");

                String response = "";
                ArrayList<Result> db = theRuns.getResults();
                response += readContents("ChronoSever/src/index.txt");
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

                response += " </tbody></table>" +
                        "</div><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>" +
					    " <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>" +
                        "</body>" +
                        "</html>";

                //write database content to web page
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
                try(Scanner fr = new Scanner(new File("ChronoSever/src/css/style.css"))){
                    while(fr.hasNextLine()){
                        response += fr.nextLine();
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
                System.out.println("couln't get file");
            }
            return responseBuilder.toString();
        }

    }


