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
                    System.out.println(str);
                    String[] strArr = str.split(" ");
                    if(strArr.length < 3)
                        chronoTimer.execute(strArr[0], strArr[1]);
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
                String[] str = stdIn.nextLine().split(" ");
                if(str.length < 3)
                    chronoTimer.execute(str[0], str[1]);
                else if(str.length == 3){
                    chronoTimer.execute(str[0], str[1],str[2]); //0 - Time, 1- - Command, 2 - Value
                }
            }
        }
        stdIn.close();
    }

    public static void execute(String command, String value){
        if(command.equalsIgnoreCase("PRINT")){
            System.out.println(value);
        }

    }

}
