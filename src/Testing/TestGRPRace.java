package Testing;

import TimingSystem.Event;
import TimingSystem.Hardware.Channel;
import TimingSystem.RaceTypes.GRP;
import TimingSystem.RaceTypes.IND;
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
            race = (GRP) f.get(event);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }


    @Test
    public void testAddRacer(){

    }

    @Test
    public void testSetStart(){


    }

    @Test
    public void testSetFinish(){


    }

    @Test
    public void testCancelRacer(){

    }

    @Test
    public void testSwap(){


    }

    @Test
    public void testClear(){

    }

    private void getLists(){
        try{
//            Field f1 = race.getClass().getDeclaredField("_racers");
//            Field f2 = race.getClass().getDeclaredField("_racerQueue");
//            Field f3 = race.getClass().getDeclaredField("_finished");
//            f1.setAccessible(true);
//            f2.setAccessible(true);
//            f3.setAccessible(true);
//            racerList = (Deque<Racer>) f1.get(race);
//            racerQueue = (Deque<Racer>) f2.get(race);
//            finished = (Deque<Racer>) f3.get(race);

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
