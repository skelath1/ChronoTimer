package Testing;
import org.junit.*;
import TimingSystem.*;
import Util.Time;
import java.util.concurrent.TimeUnit;

public class TestSim {


    @Test
    public void testSIM(){//test EVENT IND -> time and non-time commands mixed
        ChronoTimer ch = new ChronoTimer();
        ch.execute("12:14:15.222", "POWER", null);
        ch.execute("EVENT", "IND");
        ch.execute("0:14:15:223", "NEWRUN", null);
        ch.execute("tOG","1" );
        ch.execute("Tog", "2");
        ch.execute("1:15:16.355", "NUM", "234");
        ch.execute("2.33.27.345","NUM", "667");
        ch.execute("NUM", "366");
        ch.execute("TRIG", "1");
        try{
            Thread.sleep(3000);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "2");
        ch.execute("TRIG", "1");
        try{
            Thread.sleep(4000);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("triG","2" );
        ch.execute("TriG","1" );
        try{
            Thread.sleep(5000);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "2");
        ch.execute("3:43:23.351", "PRINT", null);
        ch.execute("ENDRUN", null);
        ch.execute("EXIT", null);
    }
    @Test
    public void testSIM2(){//test EVENT IND -> unneccessary time and commands
        ChronoTimer ch = new ChronoTimer();
        ch.execute("00:00:00.0", "POWER", null);
        ch.execute("EVENT", "IND");
        ch.execute("NEWRUN", null);
        ch.execute("tOG","1" );
        ch.execute("Tog", "2");
        ch.execute("NUM", "234");
        ch.execute("TRIG", "1");
        //changing time over 100 hrs
        ch.execute("TIME","02:00:01.0");
        ch.execute("TIME","03:00:00.9");
        ch.execute("TIME","04:00:00.8");
        ch.execute("TIME","05:00:00.7");
        //time limit reached
       ch.execute("TRIG", "2");
       ch.execute("PRINT", null);

    }
}
