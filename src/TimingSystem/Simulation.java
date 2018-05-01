package TimingSystem;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

import com.google.gson.Gson;

public class Simulation {
    private ChronoTimer chronoTimer;
    public Simulation(ChronoTimer chronoTimer) { this.chronoTimer = chronoTimer; }

    /**
     * Asks the user if they want to do input from file or console
     * If file option is closed it looks for input.txt for the commands
     */
    public void doInput(){
        Scanner stdIn = new Scanner(System.in);

        String s;
        do{
            System.out.print("[f]ile or [c]onsole: ");
            s = stdIn.nextLine();
        }while(!(s.equalsIgnoreCase("f") || s.equalsIgnoreCase("c")));

        if(s.equalsIgnoreCase("f")){
            // File
            try{
                stdIn = new Scanner(new File("input.txt"));

                while(stdIn.hasNext()){
                    String line = stdIn.nextLine();

                    String[] strArr = line.split("\\s+");


                    if(strArr.length ==1)
                        chronoTimer.execute(strArr[0], null,null);
                    //need to determine if it has a command value or Time command
                    else if(strArr.length ==2 && !strArr[0].contains(":"))
                        chronoTimer.execute(strArr[0], strArr[1],null);
                    //means it has no value just time + command
                    else if(strArr.length ==2 && strArr[0].contains(":"))
                        chronoTimer.execute(strArr[0], strArr[1],null,null);

                    //if it is three it can be time or it can be a third parameter with no time

                    //has the ':' so it is the time
                    else if(strArr.length == 3 && strArr[0].contains(":")){
                        chronoTimer.execute(strArr[0], strArr[1],strArr[2],null); //0 - Time, 1- - Command, 2 - Value, 3 - value2
                    }
                    //has no time but has a third parm
                    else if(strArr.length == 3){
                        chronoTimer.execute(strArr[0], strArr[1],strArr[2]); //0 - Command, 2 - Value, 3 - value2
                    }
                    //only used if given time and the CONN command which has two parameters hence 4
                    else if(strArr.length == 4){
                        chronoTimer.execute(strArr[0], strArr[1],strArr[2],strArr[3]);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.printf("Couldn't find the input file...");
            }

        } else{
            // Console
            while(true){
                String line = stdIn.nextLine();
                String[] strArr = line.split("\\s+");
                if(strArr.length == 1)
                    chronoTimer.execute(strArr[0], null,null);
                else if(strArr.length < 3)
                    chronoTimer.execute(strArr[0], strArr[1],null);
                else if(strArr.length == 3){
                    chronoTimer.execute(strArr[0], strArr[1],strArr[2]); //0 -Command, 1 - Value, 2- Value
                }
            }
        }
        stdIn.close();
    }

    public static void execute(String command, String value){
        if(command.equalsIgnoreCase("PRINT")){

            System.out.println("\n"+value);
        }
        else if(command.equalsIgnoreCase("EXIT")){
            System.exit(0);
        }
        else if(command.equalsIgnoreCase("ERROR"))
        {
            System.out.println("\n"+value);
        }

    }
    /**
     * exports the latest runs data to a txt file
     */
    public static void export(ArrayList<Run> runs, String runNumber){
        try(FileWriter writer = new FileWriter( "RUN" + runNumber + ".txt")){
           Gson gson = new Gson();
            String out = gson.toJson(runs);
            writer.write(out);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public static void export(ArrayList<Run> runs){
        try(FileWriter writer = new FileWriter( "RUNS.txt")){
            Gson gson = new Gson();
            String out = gson.toJson(runs);
            writer.write(out);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Takes in the name of test file and executes each one of the commands
     * @param testFile String
     */
    public void doInput(String testFile){
        try{
            Scanner stdIn;
            stdIn = new Scanner(new File(testFile));

            while(stdIn.hasNext()){
                String line = stdIn.nextLine();

                String[] strArr = line.split("\\s+");
                String st = strArr[0];
                if(strArr.length ==1)
                    chronoTimer.execute(strArr[0], null,null);
                    //need to determine if it has a command value or Time command
                else if(strArr.length ==2 && !strArr[0].contains(":"))
                    chronoTimer.execute(strArr[0], strArr[1],null);
                    //means it has no value just time + command
                else if(strArr.length ==2 && strArr[0].contains(":"))
                    chronoTimer.execute(strArr[0], strArr[1],null,null);


                //has the ':' so it has a time
                else if(strArr.length == 3 && strArr[0].contains(":")){
                    chronoTimer.execute(strArr[0], strArr[1],strArr[2],null); //0 - Time, 1- - Command, 2 - Value, 3 - value2
                }
                //has no time but has a third parm
                else if(strArr.length == 3){
                    chronoTimer.execute(strArr[0], strArr[1],strArr[2]); //0 - Command, 2 - Value, 3 - value2
                }
                //only used if given time and the CONN command which has two parameters hence 4
                else if(strArr.length == 4){
                    chronoTimer.execute(strArr[0], strArr[1],strArr[2],strArr[3]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the input file...");
        }
    }
}
