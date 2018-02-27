import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
public class Simulation {
    public static void main(String[] args) {
        ChronoTimer chronoTimer = new ChronoTimer();
        Scanner stdIn = new Scanner(System.in);


        //
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
                    String str = stdIn.nextLine();
                    //System.out.println(str);
                    String[] strArr = str.split("\t");
                    String st = strArr[0];
                    String[] strArr1 = strArr[1].split(" ");
                    strArr = new String[3];

                    if(strArr1.length > 1){
                        strArr[0] = st;
                        strArr[1] = strArr1[0];
                        strArr[2] = strArr1[1];
                    }
                    else{
                        strArr[0] = st;
                        strArr[1] = strArr1[0];
                    }
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
                String str = stdIn.nextLine();
                String[] strArr = str.split("\t");
                String st = strArr[0];
                String[] strArr1 = strArr[1].split(" ");
                strArr = new String[3];

                if(strArr1.length > 1){
                    strArr[0] = st;
                    strArr[1] = strArr1[0];
                    strArr[2] = strArr1[1];
                }
                else{
                    strArr[0] = st;
                    strArr[1] = strArr1[0];
                }
                if(strArr.length < 3)
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

    }

}
