package Testing;
import org.junit.*;
import Util.*;
import TimingSystem.*;

public class TestSim {


    @Test
    public void testSIM(){
        ChronoTimer ch = new ChronoTimer();
        ch.execute("12:14:15.222", "POWER", null);
        ch.execute("EVENT", "IND");
        ch.execute("0:14:15:223", "NEWRUN", null);
        ch.execute("tOG","1" );
        ch.execute("Tog", "2");
        ch.execute("1:15:16.355", "NUM", "234");
        ch.execute("TRIG", "1");
        ch.execute("TRIG", "2");
        ch.execute("2.33.27.345","NUM", "667");
        ch.execute("TRIG", "1");
        ch.execute("triG","2" );
        ch.execute("NUM", "366");
        ch.execute("TriG","1" );
        ch.execute("TRIG", "2");
        ch.execute("3:43:23.351", "PRINT", null);
        ch.execute("ENDRUN", null);
        ch.execute("EXIT", null);
    }

}
