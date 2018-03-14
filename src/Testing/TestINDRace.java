package Testing;
import TimingSystem.Event;
import TimingSystem.Hardware.Channel;
import TimingSystem.RaceTypes.IND;
import TimingSystem.Racer;
import org.junit.*;

import java.lang.reflect.Field;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestINDRace {
    Channel[] channels;
    Event event;
    IND race;
    Deque<Racer> racerList;
    Deque<Racer> racerQueue;

    @Before
    public void setUp(){
        channels = new Channel[8];
        for(int i = 0; i < 8; ++i){
            channels[i] = new Channel(i + 1);
        }
        event = new Event(channels);
        try{
            Field f = event.getClass().getDeclaredField("_racetype");
            f.setAccessible(true);
            race = (IND) f.get(event);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }


    @Test
    public void testAddRacer(){
        event.addRacer(123);
        event.addRacer(456);
        getLists();
        assertEquals("Racer 123", 123, racerList.removeFirst().getBibNumber());
        assertEquals("Racer 456", 456, racerList.removeFirst().getBibNumber());
    }

    @Test
    public void testSetStart(){
        event.addRacer(123);
        event.addRacer(456);
        event.setStartTime(500,1);
        event.setStartTime(600,1);
        getLists();

        assertEquals("Racer 123 first in racerQueue", 123, racerQueue.removeFirst().getBibNumber());
        assertEquals("Racer 456 second in racerQueue", 456, racerQueue.removeFirst().getBibNumber());
        assertTrue("racers list is empty", racerList.isEmpty());
    }

    @Test
    public void testSetFinish(){
        event.addRacer(123);
        event.addRacer(456);
        event.setStartTime(500,1);
        event.setStartTime(600,1);
        event.setFinishTime(700,2);
        event.setFinishTime(700,2);
        getLists();

        assertTrue("racerQueue is empty", racerQueue.isEmpty());
        assertEquals("Racer 123 is first in racers", 123, racerList.removeFirst().getBibNumber());
        assertEquals("Racer 456 is first in racers", 456, racerList.removeFirst().getBibNumber());

    }

    @Test
    public void testCancelRacer(){
        event.addRacer(123);
        event.addRacer(456);
        event.setStartTime(500, 1);
        event.cancelRacer();
        getLists();

        assertTrue("racerQueue is empty", racerQueue.isEmpty());
        assertEquals("Racer 123 is still first in racers", 123, racerList.removeFirst().getBibNumber());

        event.setStartTime(500,1);
        event.setStartTime(600,1);
        event.cancelRacer();

        assertEquals("Racer 456 is back in racers", 456, racerList.removeFirst().getBibNumber());
    }

    private void getLists(){
        try{
            Field f1 = race.getClass().getDeclaredField("_racers");
            Field f2 = race.getClass().getDeclaredField("_racerQueue");
            f1.setAccessible(true);
            f2.setAccessible(true);
            racerList = (Deque<Racer>) f1.get(race);
            racerQueue = (Deque<Racer>) f2.get(race);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
