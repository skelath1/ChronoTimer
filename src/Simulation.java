import java.util.Scanner;

public class Simulation {
    public static void main(String[] args) {
        ChronoTimer c = new ChronoTimer();
        Scanner stdIn = new Scanner(System.in);

    }

    public void execute(String command, String value){
        if(command.equalsIgnoreCase("PRINT")){
            System.out.println(value);
        }

    }
}
