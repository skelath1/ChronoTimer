package Testing;

import TimingSystem.Event;
import TimingSystem.Hardware.Channel;
import TimingSystem.RaceTypes.PARIND;
import TimingSystem.Racer;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPARINDRace {
    Channel[] channels;
    Event event;
    PARIND race;
    Deque<Racer> finished;

    //_racersL
    Deque<Racer> leftQueue;
    //_racersR
    Deque<Racer> rightQueue;

    Deque<Racer> left;
    Deque<Racer> right;

    @Before
    public void setUp(){
        channels = new Channel[8];
        for(int i = 0; i < 8; ++i){
            channels[i] = new Channel(i + 1);
        }
        event = new Event("PARIND");
        try{
            Field f = event.getClass().getDeclaredField("_racetype");
            f.setAccessible(true);
            race = (PARIND) f.get(event);
            assertTrue(race.toString().equalsIgnoreCase("PARIND"));

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    //TODO fix queues for races

    @Test
    public void testAddRacer(){
        event.addRacer(123);
        event.addRacer(456);
        getLists();
        assertEquals("Racer 123", 123, leftQueue.removeFirst().getBibNumber());
        assertEquals("Racer 456", 456, rightQueue.removeFirst().getBibNumber());
    }

    @Test
    public void testSetStart(){
        // One Racer
        event.addRacer(123);
        event.setTime(500, 1);
        getLists();
        assertEquals("Racer 123 in Left Queue", 123, left.removeFirst().getBibNumber());
        assertTrue(leftQueue.isEmpty());
        event.clear();

        // Two Racers
        event.addRacer(123);
        event.addRacer(456);
        event.setTime(500,1);
        event.setTime(700, 3);
        getLists();

        assertEquals("Racer 123 in Left Queue", 123, left.removeFirst().getBibNumber());
        assertEquals("Racer 456 in Right Queue", 456, right.removeFirst().getBibNumber());
        assertTrue(leftQueue.isEmpty() && rightQueue.isEmpty());
        event.clear();

        // Three Racers
        event.addRacer(123);
        event.addRacer(456);
        event.addRacer(789);
        event.setTime(500,1);
        event.setTime(700, 3);
        event.setTime(900, 1);
        getLists();

        assertEquals("Racer 123 in Left Queue", 123, left.removeFirst().getBibNumber());
        assertEquals("Racer 456 in Right Queue", 456, right.removeFirst().getBibNumber());
        assertEquals("Racer 789 in Left Queue", 789, left.removeFirst().getBibNumber());
        assertTrue(leftQueue.isEmpty() && rightQueue.isEmpty());
        event.clear();
    }

    @Test
    public void testSetFinish(){
        // One Racer
        event.addRacer(123);
        event.setTime(500, 1);
        event.setTime(600, 2);
        getLists();

        assertTrue(left.isEmpty());
        assertEquals("Racer 123 in Racer List", 123, finished.removeFirst().getBibNumber());
        event.clear();

        // Two Racers
        event.addRacer(123);
        event.addRacer(456);
        event.setTime(500,1);
        event.setTime(700, 3);
        event.setTime(600, 2);
        event.setTime(800, 4);
        getLists();

        assertTrue(left.isEmpty());
        assertTrue(right.isEmpty());
        assertEquals("Racer 123 in Racer List", 123, finished.removeFirst().getBibNumber());
        assertEquals("Racer 456 in Racer List", 456, finished.removeFirst().getBibNumber());

        event.clear();

        // Three Racers
        event.addRacer(123);
        event.addRacer(456);
        event.addRacer(789);
        event.setTime(500,1);
        event.setTime(700, 3);
        event.setTime(900, 1);
        event.setTime(600, 2);
        event.setTime(800, 4);
        event.setTime(1000, 2);
        getLists();

        assertTrue(left.isEmpty());
        assertTrue(right.isEmpty());
        assertEquals("Racer 123 in Racer List", 123, finished.removeFirst().getBibNumber());
        assertEquals("Racer 456 in Racer List", 456, finished.removeFirst().getBibNumber());
        assertEquals("Racer 789 in Racer List", 789, finished.removeFirst().getBibNumber());

        event.clear();

    }

    @Test
    public void testCancelRacer(){
        event.addRacer(123);
        event.addRacer(456);
        event.setTime(500,1);
        event.cancelRacer();
        getLists();

        assertTrue(left.isEmpty());
        assertEquals("Racer 123 is still first in racers", 123, leftQueue.removeFirst().getBibNumber());
        event.clear();

        event.addRacer(123);
        event.addRacer(456);

        event.setTime(500,1);
        event.setTime(600, 3);
        event.cancelRacer();
        getLists();

        assertEquals("Racer 456 is back in racers", 456, rightQueue.removeFirst().getBibNumber());
        event.clear();
    }

    @Test
    public void testClear(){
        event.addRacer(123);
        event.addRacer(456);
        event.setTime(100, 1);
        event.clear();
        getLists();

        assertTrue(rightQueue.isEmpty() && leftQueue.isEmpty() && left.isEmpty() && right.isEmpty());

        event.addRacer(123);
        event.addRacer(456);
        event.setTime(100, 1);
        event.clear(123);
        getLists();

        assertTrue(left.isEmpty());
        assertTrue(rightQueue.getFirst().getBibNumber() == 456);

        event.clear(456);
        getLists();

        assertTrue(rightQueue.isEmpty());

        event.clear();
    }

    private void getLists(){
        try{
            Field f1 = race.getClass().getDeclaredField("_racersL");
            Field f2 = race.getClass().getDeclaredField("_racersR");
            Field f3 = race.getClass().getDeclaredField("_right");
            Field f4 = race.getClass().getDeclaredField("_left");
            Field f5 = race.getClass().getDeclaredField("_finished");
            f1.setAccessible(true);
            f2.setAccessible(true);
            f3.setAccessible(true);
            f4.setAccessible(true);
            f5.setAccessible(true);
            leftQueue = (Deque<Racer>) f1.get(race);
            rightQueue = (Deque<Racer>) f2.get(race);
            right = (Deque<Racer>) f3.get(race);
            left = (Deque<Racer>) f4.get(race);
            finished = (Deque<Racer>) f5.get(race);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
