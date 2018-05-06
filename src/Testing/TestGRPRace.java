package Testing;

import TimingSystem.Event;
import TimingSystem.Hardware.Channel;
import TimingSystem.RaceTypes.GRP;
import TimingSystem.Racer;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGRPRace {
    Channel[] channels;
    Event event;
    GRP race;

    Deque<Racer> racerList;
    Deque<Racer> racerQueue;
    Deque<Racer> finished;





    @Before
    public void setUp(){
        channels = new Channel[8];
        for(int i = 0; i < 8; ++i){
            channels[i] = new Channel(i + 1);
        }
        event = new Event("GRP");
        try{
            Field f = event.getClass().getDeclaredField("_racetype");
            f.setAccessible(true);
            race = (GRP) f.get(event);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }


    @Test
    public void testAddRacer(){
        event.addRacer(123);
        event.addRacer(123);
        getLists();

        assertEquals(racerList.size(), 1);

        event.addRacer(456);
        getLists();

        assertTrue(racerList.removeFirst().getBibNumber() == 123);
        assertTrue(racerList.removeFirst().getBibNumber() == 456);
    }

    @Test
    public void testSetStart(){
        event.addRacer(123);
        event.addRacer(456);

        event.setTime(100, 1);
        getLists();

        assertEquals(racerQueue.size(), 2);
        assertTrue(racerList.isEmpty());
        assertTrue(racerQueue.removeFirst().getBibNumber() == 123);
        assertTrue(racerQueue.removeFirst().getBibNumber() == 456);
    }


    @Test
    public void testAnonRacers(){
        event.setTime(100, 1);
        getLists();

        assertTrue(racerQueue.isEmpty());

        event.setTime(200, 2);
        event.setTime(300, 2);
        getLists();

        assertTrue(racerQueue.isEmpty());
        assertEquals(99901, finished.removeFirst().getBibNumber());
        assertEquals(99902, finished.removeFirst().getBibNumber());
        event.clear();

        event.setTime(100, 1);
        event.setTime(200, 2);
        event.setTime(300, 2);
        event.addRacer(1);
        event.addRacer(1);
        event.addRacer(2);
        getLists();

        assertEquals(1, finished.removeFirst().getBibNumber());
        assertEquals(2, finished.removeFirst().getBibNumber());

    }


    @Test
    public void testSetFinish(){
        event.addRacer(123);
        event.addRacer(456);

        event.setTime(100, 1);

        event.setTime(400,2);
        event.setTime(500, 2);

        getLists();
        assertTrue(racerQueue.isEmpty());
        assertEquals(123, finished.removeFirst().getBibNumber());
        assertEquals(456, finished.removeFirst().getBibNumber());
    }

    @Test
    public void testClear(){
        event.addRacer(123);
        event.addRacer(456);
        event.addRacer(789);

        event.setTime(100, 1);
        event.setTime(200, 1);

        event.setTime(300, 2);
        event.clear();
        getLists();
        assertTrue(racerList.isEmpty() && racerQueue.isEmpty()&& finished.isEmpty());

        event.addRacer(123);
        event.addRacer(456);
        event.addRacer(789);
        event.setTime(100, 1);

        event.clear(123);
        getLists();

        assertTrue(racerQueue.getFirst().getBibNumber() != 123);

        event.clear(456);
        getLists();

        assertTrue(racerQueue.getFirst().getBibNumber() != 456);
    }

    private void getLists(){
        try{
            Field f1 = race.getClass().getDeclaredField("_racers");
            Field f2 = race.getClass().getDeclaredField("_racerQueue");
            Field f3 = race.getClass().getDeclaredField("_finished");
            f1.setAccessible(true);
            f2.setAccessible(true);
            f3.setAccessible(true);
            racerList = (Deque<Racer>) f1.get(race);
            racerQueue = (Deque<Racer>) f2.get(race);
            finished = (Deque<Racer>) f3.get(race);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
