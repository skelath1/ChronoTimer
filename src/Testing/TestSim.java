package Testing;
import org.junit.*;
import TimingSystem.*;
import Util.Time;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

public class TestSim {

    Simulation sim = new Simulation(new ChronoTimer());
    @Test
    public void testIndividualOne(){
        sim.doInput("TestFiles/Individual1.txt");
    }
    @Test
    public void testIndividualTwo(){
        sim.doInput("TestFiles/Individual2.txt");
    }
    @Test
    public void testParallelIndividualOne(){
        sim.doInput("TestFiles/ParallelIND.txt");
    }

    @Test
    public void testParallelGroupOne(){
        sim.doInput("TestFiles/ParallelGroup.txt");
    }
    @Test
    public void testDNF(){sim.doInput("TestFiles/TestDNF");}
}

