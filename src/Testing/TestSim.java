package Testing;
import org.junit.*;
import TimingSystem.*;
import Util.Time;
import java.util.concurrent.TimeUnit;

public class TestSim {


    @Test
    public void testSIM(){//test EVENT IND -> time and non-time commands mixed
        /*
        ChronoTimer ch = new ChronoTimer();
        ch.execute("12:14:15.222", "POWER", null);
        ch.execute("EVENT", "IND");
        ch.execute("0:14:15.223", "NEWRUN", null);
        ch.execute("tOG","1" );
        ch.execute("Tog", "2");
        ch.execute("1:15:16.355", "NUM", "234");
        ch.execute("2:33:27.345","NUM", "667");
        ch.execute("NUM", "366");
        ch.execute("TRIG", "1");
        try{
            Thread.sleep(500);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "2");
        ch.execute("TRIG", "1");
        try{
            Thread.sleep(500);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("triG","2" );
        ch.execute("TriG","1" );
        try{
            Thread.sleep(500);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "2");
        ch.execute("3:43:23.351", "PRINT", null);
        ch.execute("ENDRUN", null);
        ch.execute("EXIT", null);
    }
    @Test
    public void testSIM2(){//test EVENT IND -> time change during triggers
        ChronoTimer ch = new ChronoTimer();
        ch.execute("00:00:00.0", "POWER", null);
        ch.execute("EVENT", "IND");
        ch.execute("NEWRUN", null);
        ch.execute("tOG","1" );
        ch.execute("Tog", "2");
        ch.execute("NUM", "234");
        ch.execute("TRIG", "1");
        //changing time
        ch.execute("TIME","02:00:01.0");
        System.out.println(ch.getSysTime().getSysTime());
        ch.execute("TIME","03:00:00.9");
        System.out.println(ch.getSysTime().getSysTime());
        ch.execute("TIME","04:00:00.8");
        System.out.println(ch.getSysTime().getSysTime());
        ch.execute("TIME","05:00:00.7");
        System.out.println(ch.getSysTime().getSysTime());
        //time changed
       ch.execute("TRIG", "2");
       ch.execute("PRINT", null);

    }
    @Test
    public void testSIM3(){//test EVENT PARIND -> multi events
        ChronoTimer ch = new ChronoTimer();
        ch.execute("3:00:00.0", "POWER", null);
        ch.execute("TIME","4:00:00.2");
        ch.execute("EVENT", "PARIND");
        ch.execute("NEWRUN", null);
        ch.execute("tOG","1" );
        ch.execute("tOG","1" );
        ch.execute("tOG","1" );
        ch.execute("Tog", "2");
        ch.execute("tOG","4" );
        ch.execute("tOG","3" );
        ch.execute("NUM", "55");
        ch.execute("NUM", "56");
        ch.execute("NUM", "57");
        ch.execute("NUM", "58");
        ch.execute("TRIG", "1");
        try{
            Thread.sleep(250);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "3");
        try{
            Thread.sleep(250);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "3");
        try{
            Thread.sleep(250);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "1");
        ch.execute("TRIG", "2");
        try{
            Thread.sleep(250);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "2");
        ch.execute("TRIG", "4");
        try{
            Thread.sleep(250);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "4");
        ch.execute("PRINT", null);
        ch.execute("ENDRUN", null);
        ch.execute("EVENT", "IND");
        ch.execute("NEWRUN", null);
        ch.execute("tOG","1" );
        ch.execute("Tog", "2");
        ch.execute("NUM", "234");
        ch.execute("TRIG", "1");
        ch.execute("TRIG", "1");
        try{
            Thread.sleep(250);
        }catch(Exception ex){
            System.out.println("Interrupted...");
        }
        ch.execute("TRIG", "2");
        ch.execute("PRINT", null);
        ch.execute("ENDRUN", null);
        ch.execute("EXPORT","1" );
        ch.execute("EXPORT", "2");
        ch.execute("POWER", null);
        ch.execute("EXIT", null);
        */
    }
}
