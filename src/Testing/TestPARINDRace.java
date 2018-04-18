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
    Deque<Racer> leftQueue;
    Deque<Racer> rightQueue;
    Deque<Racer> left;
    Deque<Racer> right;

    @Before
    public void setUp(){
        channels = new Channel[8];
        for(int i = 0; i < 8; ++i){
            channels[i] = new Channel(i + 1);
        }
        event = new Event("PARIND",channels);

        try{
            Field f = event.getClass().getDeclaredField("_racetype");
            f.setAccessible(true);
            race = (PARIND) f.get(event);
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
        event.setStartTime(500, 1);
        getLists();
//        assertEquals("Racer 123 in Left Queue", 123, leftQueue.removeFirst().getBibNumber());
//        assertTrue(racerList.isEmpty());
        event.clear();

        // Two Racers
        event.addRacer(123);
        event.addRacer(456);
        event.setStartTime(500,1);
        event.setStartTime(700, 3);
        getLists();

//        assertEquals("Racer 123 in Left Queue", 123, leftQueue.removeFirst().getBibNumber());
//        assertEquals("Racer 456 in Right Queue", 456, rightQueue.removeFirst().getBibNumber());
//        assertTrue(racerList.isEmpty());
        event.clear();

        // Three Racers
        event.addRacer(123);
        event.addRacer(456);
        event.addRacer(789);
        event.setStartTime(500,1);
        event.setStartTime(700, 3);
        event.setStartTime(900, 1);
        getLists();

//        assertEquals("Racer 123 in Left Queue", 123, leftQueue.removeFirst().getBibNumber());
//        assertEquals("Racer 456 in Right Queue", 456, rightQueue.removeFirst().getBibNumber());
//        assertEquals("Racer 789 in Left Queue", 789, leftQueue.removeFirst().getBibNumber());
//        assertTrue(racerList.isEmpty());
        event.clear();
    }

    @Test
    public void testSetFinish(){
        // One Racer
        event.addRacer(123);
        event.setStartTime(500, 1);
        event.setFinishTime(600, 2);
        getLists();

//        assertTrue(leftQueue.isEmpty());
//        assertEquals("Racer 123 in Racer List", 123, racerList.removeFirst().getBibNumber());
        event.clear();

        // Two Racers
        event.addRacer(123);
        event.addRacer(456);
        event.setStartTime(500,1);
        event.setStartTime(700, 3);
        event.setFinishTime(600, 2);
        event.setFinishTime(800, 4);
        getLists();

//        assertTrue(leftQueue.isEmpty());
//        assertTrue(rightQueue.isEmpty());
//        assertEquals("Racer 123 in Racer List", 123, racerList.removeFirst().getBibNumber());
//        assertEquals("Racer 456 in Racer List", 456, racerList.removeFirst().getBibNumber());

        event.clear();

        // Three Racers
        event.addRacer(123);
        event.addRacer(456);
        event.addRacer(789);
        event.setStartTime(500,1);
        event.setStartTime(700, 3);
        event.setStartTime(900, 1);
        event.setFinishTime(600, 2);
        event.setFinishTime(800, 4);
        event.setFinishTime(1000, 2);
        getLists();

//        assertTrue(leftQueue.isEmpty());
//        assertTrue(rightQueue.isEmpty());
//        assertEquals("Racer 123 in Racer List", 123, racerList.removeFirst().getBibNumber());
//        assertEquals("Racer 456 in Racer List", 456, racerList.removeFirst().getBibNumber());
//        assertEquals("Racer 789 in Racer List", 789, racerList.removeFirst().getBibNumber());

        event.clear();

    }

    @Test
    public void testCancelRacer(){
        event.addRacer(123);
        event.addRacer(456);
        event.setStartTime(500,1);
        event.cancelRacer();
        getLists();
//
//        assertTrue(leftQueue.isEmpty());
//        assertEquals("Racer 123 is still first in racers", 123, racerList.removeFirst().getBibNumber());

        event.setStartTime(500,1);
        event.setStartTime(600, 3);
        event.cancelRacer();

//        assertEquals("Racer 456 is back in racers", 456, racerList.removeFirst().getBibNumber());
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
