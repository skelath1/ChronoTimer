package Testing;

import TimingSystem.Event;
import TimingSystem.RaceTypes.PARGRP;
import TimingSystem.Racer;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPARGRP {
    Event event;
    PARGRP race;

    Deque<Racer> racers;
    HashMap<Integer, Racer> racerMap;
    Deque<Racer> finished;

    @Before
    public void setUp(){
        event = new Event("PARGRP");
        try{
            Field f = event.getClass().getDeclaredField("_racetype");
            f.setAccessible(true);
            race = (PARGRP) f.get(event);
        } catch(Exception ex){}
    }

    @Test
    public void restAddRacer(){
        event.addRacer(123);
        event.addRacer(123);
        getLists();

        assertEquals(racers.size(), 1);

        event.addRacer(456);
        getLists();

        assertTrue(racers.removeFirst().getBibNumber() == 123);
        assertTrue(racers.removeFirst().getBibNumber() == 456);
    }

    @Test
    public void testSetStart(){
        event.addRacer(123);
        event.addRacer(456);

        event.setTime(100, 1);
        getLists();

        assertEquals(racerMap.size(), 2);
        assertTrue(racers.isEmpty());
        assertTrue(racerMap.get(1).getBibNumber() == 123);
        assertTrue(racerMap.get(2).getBibNumber() == 456);

    }

    @Test
    public void testSetFinish(){
        event.addRacer(123);
        event.addRacer(456);

        event.setTime(100, 1);

        event.setTime(400, 1);
        event.setTime(500, 2);
        event.setTime(500, 2);
        getLists();

        assertTrue(racerMap.size() == 0);
        assertEquals(123, finished.removeFirst().getBibNumber());
        assertEquals(456, finished.removeFirst().getBibNumber());
    }

    @Test
    public void testClear(){
        event.addRacer(123);
        event.addRacer(456);
        event.addRacer(789);

        event.setTime(100, 1);
        event.setTime(200, 2);
        event.setTime(300, 3);
        event.clear();
        getLists();

        assertTrue(racers.isEmpty() && racerMap.isEmpty() && finished.isEmpty());

        event.addRacer(123);
        event.addRacer(456);
        event.addRacer(789);
        event.setTime(100, 1);

        assertTrue(racerMap.get(1).getBibNumber() == 123);
        event.clear(123);
        getLists();

        assertTrue(null == racerMap.get(1));

        assertTrue(racerMap.get(2).getBibNumber() == 456);
        event.clear(456);
        getLists();

        assertTrue(null == racerMap.get(2));
    }


    private void getLists(){
        try{
            Field f1 = race.getClass().getDeclaredField("_racers");
            Field f2 = race.getClass().getDeclaredField("_racerMap");
            Field f3 = race.getClass().getDeclaredField("_finished");
            f1.setAccessible(true);
            f2.setAccessible(true);
            f3.setAccessible(true);
            racers = (Deque<Racer>) f1.get(race);
            racerMap = (HashMap<Integer, Racer>) f2.get(race);
            finished = (Deque<Racer>) f3.get(race);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
