package Testing;
import TimingSystem.Racer;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRacer {
    Racer r1 = new Racer(1);
    Racer r2 = new Racer(2);

    @Test
    public void test1(){
        assertEquals("The Racer had the wrong bib number",1,r1.getBibNumber());
        assertEquals("The Racer had the wrong bib number",2,r2.getBibNumber());
    }
    @Test
    public void test2(){

        //-1 represents DNF
        assertEquals("Wrong finish time",-1,r1.getFinishTime());
        assertEquals("Wrong finish time",-1,r1.getStartTime());

        r1.setStartTime(1234);
        assertEquals("Racer hasn't finished yet so it is DNF",-1,r1.getFinishTime());
        r1.setFinishTime(2345);
        assertFalse("Should not be DNF",r1.getFinishTime() == -1);

    }
}
