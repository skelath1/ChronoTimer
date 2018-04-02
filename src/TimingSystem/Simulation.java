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
    public Simulation(ChronoTimer chronoTimer) {
        this.chronoTimer = chronoTimer;
    }
    public void doInput(){

        Scanner stdIn = new Scanner(System.in);

        String s;
        do{
            System.out.print("[f]ile or [c]onsole: ");
            s = stdIn.nextLine();
        }while(!(s.equalsIgnoreCase("f") || s.equalsIgnoreCase("c")));
        boolean addTimeStamp = true;
        if(s.equalsIgnoreCase("f")){
            // File
            try{
                stdIn = new Scanner(new File("input.txt"));

                while(stdIn.hasNext()){
                    String line = stdIn.nextLine();

                    String[] strArr = line.split("\\s+");
                    String st = strArr[0];
                    //if(strArr.length > 1){
                       // String[] strArr1 = strArr[1].split(" ");

                        //if(strArr1.length > 1){
                         //   strArr = new String[3];

                         //   strArr[0] = st;
                        //    strArr[1] = strArr1[0];
                       //     strArr[2] = strArr1[1];
                       // }
                        //else{
                        //    strArr = new String[2];
                       //     strArr[0] = st;
                       //     strArr[1] = strArr1[0];
                     //   }
                   // }

                    if(strArr.length ==1)
                        chronoTimer.execute(strArr[0], null);
                    //need to determine if it has a command value or Time command
                    else if(strArr.length ==2 && !strArr[0].contains(":"))
                        chronoTimer.execute(strArr[0], strArr[1]);
                    //means it has no value just time + command
                    else if(strArr.length ==2 && strArr[0].contains(":"))
                        chronoTimer.execute(strArr[0], strArr[1],null);
                    else if(strArr.length == 3){
                        chronoTimer.execute(strArr[0], strArr[1],strArr[2]); //0 - Time, 1- - Command, 2 - Value
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else{
            // Console
            while(true){
                String line = stdIn.nextLine();
                String[] strArr = line.split(" ");
                if(strArr.length == 1)
                    chronoTimer.execute(strArr[0], null);
                else if(strArr.length < 3)
                    chronoTimer.execute(strArr[0], strArr[1]);
                else if(strArr.length == 3){
                    chronoTimer.execute(strArr[0], strArr[1],strArr[2]); //0 - Time, 1- - Command, 2 - Value
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
            System.exit(1);
        }

    }
    /**
     * This method exports the latest runs data to a txt file
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
}
