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
        sim.enableFileOption("TestFiles/Individual1.txt");
    }
    @Test
    public void testIndividualTwo(){
        sim.enableFileOption("TestFiles/Individual2.txt");
    }
    @Test
    public void testParallelIndividualOne(){
        sim.enableFileOption("TestFiles/ParallelIND.txt");
    }
    @Test
    public void testParallelGroupOne(){
        sim.enableFileOption("TestFiles/ParallelGroup.txt");
    }
    @Test
    public void testGroupOne(){
        sim.enableFileOption("TestFiles/Group.txt");
    }
}

