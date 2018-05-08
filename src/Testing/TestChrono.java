package Testing;
import TimingSystem.ChronoTimer;
import TimingSystem.Event;
import TimingSystem.Hardware.Channel;
import TimingSystem.RaceTypes.RaceType;
import TimingSystem.Run;
import org.junit.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestChrono {
    ChronoTimer ct;
    Method m;
    Field runField;
    Field eventField;
    Field channelField;

    Channel []channels;
    String state;
    Boolean runCalled;
    Boolean eventCalled;
    Event event;
    Field raceTypeField;
    RaceType raceType;
    @Before
    public void setUp(){
        ct = new ChronoTimer();
    }

    @Test
    public void testState(){
        getState();
        assertTrue(state.equalsIgnoreCase("OFF"));

        ct.execute("POWER", null,null);
        getState();
        assertTrue(state.equalsIgnoreCase("ON"));


        ct.execute("EVENT", "IND",null);
        getState();
        assertTrue(state.equalsIgnoreCase("EVENT"));

        ct.execute("NEWRUN", null,null);
        getState();

        ct.execute("TOG", "1",null);
        getState();
        assertTrue(state.equalsIgnoreCase("EVENT"));

        ct.execute("POWER",null,null);
        getState();
        assertTrue("Incorrect State: " + state.toString(),state.equalsIgnoreCase("OFF"));

        ct.execute("POWER",null,null);
        getState();
        assertTrue(state.equalsIgnoreCase("ON"));


        ct.execute("NEWRUN",null,null);
        getState();
        assertTrue(state.equalsIgnoreCase("ON"));

    }
    @Test
    public void test6(){
        getRunCalled();
        getEventCalled();
        assertFalse(runCalled);
        assertFalse(eventCalled);

        ct.execute("POWER", null,null);
        ct.execute("NEWRUN", null,null);
        getRunCalled();
        getEventCalled();
        assertTrue(runCalled);
        assertFalse(eventCalled);

        ct.execute("NEWRUN", null,null);
        getRunCalled();
        getEventCalled();
        assertTrue(runCalled);
        assertFalse(eventCalled);

        ct.execute("EVENT", null,null);
        getRunCalled();
        getEventCalled();
        assertTrue(runCalled);
        assertTrue(eventCalled);

        ct.execute("POWER", null,null);
        getRunCalled();
        getEventCalled();
        assertFalse(runCalled);
        assertFalse(eventCalled);

        ct.execute("POWER", null,null);
        ct.execute("NEWRUN", null,null);
        getRunCalled();
        getEventCalled();
        assertTrue(runCalled);
        assertFalse(eventCalled);

        ct.execute("TOG", "1",null);
        getRunCalled();
        getEventCalled();
        assertTrue(runCalled);
        assertTrue(eventCalled);

        ct.execute("POWER", null,null);
        getRunCalled();
        getEventCalled();
        assertFalse(runCalled);
        assertFalse(eventCalled);

        ct.execute("POWER", null,null);
        ct.execute("EVENT", "IND",null);
        getRunCalled();
        getEventCalled();
        assertFalse(runCalled);
        assertTrue(eventCalled);

        //testing after endRun called
        ct.execute("NEWRUN", null,null);
        ct.execute("ENDRUN", null,null);
        getRunCalled();
        getEventCalled();
        assertFalse(runCalled);
        assertFalse(eventCalled);
    }
    @Test
    public void test1(){
        getState();
        assertTrue(state.equalsIgnoreCase("OFF"));
        ct.execute("POWER", null,null);
        getState();
        assertTrue(state.equalsIgnoreCase("ON"));

        ct.execute("POWER", null,null);
        getState();
        assertTrue(state.equalsIgnoreCase("OFF"));

        ct.execute("POWER", null,null);
        ct.execute("EVENT", "IND",null);
        ct.execute("POWER", null,null);
        getState();
        assertTrue(state.equalsIgnoreCase("OFF"));
        getRunCalled();
        getEventCalled();
        assertFalse(runCalled);
        assertFalse(eventCalled);

    }
    @Test
    public void test2(){
        ct.execute("POWER", null,null);
        ct.execute("TOG", "1",null);
        ct.execute("CONN", "Gate","1");
        getChannels();
        assertTrue(channels[0].isReady());
        ct.execute("TOG", "1",null);
        getChannels();
        assertFalse(channels[0].isReady());



    }
    @Test
    public void test3(){
        ct.execute("POWER", null,null);
        ct.execute("RESET", null,null);
        getState();
        getChannels();
        getRunCalled();
        getEventCalled();
        assertFalse(channels[0].isReady());
        assertFalse(runCalled);
        assertFalse(eventCalled);
        assertTrue(state.equalsIgnoreCase("ON"));

        ct.execute("POWER", null,null);
        ct.execute("TOG", "1",null);
        ct.execute("CONN", "Gate","1");
        ct.execute("RESET", null,null);
        getChannels();
        getRunCalled();
        getEventCalled();
        assertFalse(channels[0].isReady());
        assertFalse(runCalled);
        assertFalse(eventCalled);
        assertTrue(state.equalsIgnoreCase("ON"));

    }@Test
    public void test4(){
        //done in event test files


    }@Test
    public void test5(){
        //cant test system.exit
    }

    //test 6 is done above

    @Test
    public void test7(){
        //testing time in in TestTime
    }
    @Test
    public void test8(){
      //Testing conn command
        ct.execute("POWER", null,null);
        ct.execute("TOG", "1",null);
        ct.execute("CONN", "Gate","1");
        getChannels();
        assertTrue(channels[0].isReady());

        //disconnect turn off and try to connect sensor
        ct.execute("DISC", "1",null);
        ct.execute("POWER", null,null);
        ct.execute("TOG", "1",null);
        ct.execute("CONN", "Gate","1");
        getChannels();
        assertFalse(channels[0].isReady());
    }
    @Test
    public void test9(){
        //disconnect
        ct.execute("POWER", null,null);
        ct.execute("TOG", "1",null);
        ct.execute("CONN", "Gate","1");
        ct.execute("DISC", "1",null);
        getChannels();
        assertTrue(channels[0].isReady());

        //disconnect turn off and try to connect sensor
        ct.execute("POWER", null,null);
        ct.execute("CONN", "Gate","1");
        ct.execute("DISC", "1",null);
        getChannels();
        assertFalse(channels[0].isReady());


    }@Test
    public void test10(){
        ct.execute("event",null,null);
        getEventCalled();
        getState();
        assertTrue(state.equalsIgnoreCase("off"));
        assertFalse(eventCalled);

        ct.execute("power",null,null);
        ct.execute("event",null,null);
        getEventCalled();
        getState();
        assertTrue(state.equalsIgnoreCase("event"));
        assertTrue(eventCalled);

        ct.execute("event",null,null);
        getState();
        getEventCalled();
        assertTrue(state.equalsIgnoreCase("event"));
        assertTrue(eventCalled);
    }
    @Test
    public void test11(){

        ct.execute("newrun",null,null);
        getRunCalled();
        assertFalse(runCalled);
        ct.execute("power",null,null);
        ct.execute("newrun",null,null);
        getRunCalled();
        assertTrue(runCalled);

    }
    @Test
    public void test12(){
        ct.execute("endrun",null,null);
        getState();
        getRunCalled();
        getEventCalled();
        assertTrue(state.equalsIgnoreCase("off"));
        assertFalse(eventCalled);
        assertFalse(runCalled);
        ct.execute("power", null,null);
        ct.execute("endrun",null,null);
        getState();
        getRunCalled();
        getEventCalled();
        assertTrue(state.equalsIgnoreCase("on"));
        assertFalse(eventCalled);
        assertFalse(runCalled);
        ct.execute("newrun",null,null);
        ct.execute("endrun",null,null);
        getState();
        getEventCalled();
        getRunCalled();
        assertTrue(state.equalsIgnoreCase("on"));
        assertFalse(eventCalled);
        assertTrue(runCalled);
        ct.execute("newrun",null,null);
        ct.execute("event",null,null);
        ct.execute("endrun",null,null);
        getState();
        getEventCalled();
        getRunCalled();
        assertTrue(state.equalsIgnoreCase("on"));
        assertFalse(eventCalled);
        assertFalse(runCalled);
    }
    @Test
    public void test13(){
        ct.execute("power", null,null);
        ct.execute("print",null,null);

        ct.execute("event",null,null);
        ct.execute("newrun",null,null);
        ct.execute("CONN", "GATE", "1");
        ct.execute("CONN", "GATE", "2");
        ct.execute("num","10",null);
        ct.execute("tog","1",null);
        ct.execute("tog","2",null);
        ct.execute("trig","1",null);
        ct.execute("print",null,null);

        ct.execute("reset",null,null);
        ct.execute("event",null,null);
        ct.execute("newrun",null,null);
        ct.execute("CONN", "GATE", "1");
        ct.execute("CONN", "GATE", "3");
        ct.execute("num","10",null);
        ct.execute("tog","1",null);
        ct.execute("tog","2",null);
        ct.execute("trig","1",null);
        ct.execute("trig","2",null);
        ct.execute("endrun",null,null);
        ct.execute("print",null,null);



        ct.execute("newrun",null,null);
        ct.execute("num","12",null);
        ct.execute("num","13",null);
        ct.execute("tog","1",null);
        ct.execute("tog","2",null);
        ct.execute("trig","1",null);
        ct.execute("trig","2",null);
        ct.execute("trig","1",null);
        ct.execute("trig","2",null);
        ct.execute("endrun",null,null);
        ct.execute("print","1",null);

    }@Test
    public void test14(){
        ct.execute("power", null,null);
        ct.execute("event",null,null);
        ct.execute("newrun",null,null);
        ct.execute("num","45",null);
        ct.execute("num","167",null);
        ct.execute("tog","1",null);
        ct.execute("tog","2",null);
        ct.execute("trig","1",null);
        ct.execute("trig","2",null);
        ct.execute("trig","1",null);
        ct.execute("trig","2",null);
        ct.execute("endrun",null,null);
        ct.execute("export",null,null);
        ct.execute("export","1",null);
    }
    @Test
    public void test15(){
		//testing NUM
		ct.execute("POWER", null, null);
		ct.execute("NEWRUN", null, null);
		ct.execute("NUM", "211", null);
		getRacetype();
		assertTrue(raceType.getRuns().isEmpty());
		ct.execute("CONN", "GATE", "1");
		ct.execute("CONN", "GATE", "3");
		ct.execute("TOG", "1", null);
		ct.execute("TOG", "3", null);
		ct.execute("TRIG", "1", null);
		ct.execute("TRIG", "3", null);
		ct.execute("ENDRUN", null,null);
		assertFalse(raceType.getRuns().isEmpty());


	}
    @Test
    public void test16(){
//Testing clear
		ct.execute("POWER", null, null);
		ct.execute("NEWRUN", null, null);
		ct.execute("NUM", "211", null);
		getRacetype();
		ct.execute("CONN", "GATE", "1");
		ct.execute("CONN", "GATE", "3");
		ct.execute("TOG", "1", null);
		ct.execute("TOG", "3", null);
		ct.execute("TRIG", "1", null);
		ct.execute("TRIG", "3", null);
		ct.execute("CLEAR", null, null);
		assertTrue(raceType.getRuns().isEmpty());
		ct.execute("ENDRUN",null,null);
		ct.execute("EVENT", "PARIND", null);
		ct.execute("NEWRUN", null, null);
		ct.execute("NUM", "344", null);
		ct.execute("NUM", "555", null);
		ct.execute("TRIG", "1", null);
		ct.execute("TRIG", "3", null);
		ct.execute("TRIG", "3", null);
        ct.execute("CLEAR", null, null);
		assertFalse(raceType.getRuns().isEmpty());
    }
    @Test
    public void test17(){
		//For swap, see TESTINDRace -> testSwap()
    }
    @Test
    public void test18(){
		//Testing DNF, see TestSim -> testDNF()
    }@Test
    public void test19(){//testing trig
        ct.execute("POWER", null, null);
        ct.execute("EVENT", "IND", null);
        ct.execute("NEWRUN", null, null);
        ct.execute("NUM", "211", null);
        ct.execute("NUM", "311", null);
        getRacetype();
        assertTrue(raceType.getRuns().isEmpty());
        ct.execute("CONN", "GATE", "1");
        ct.execute("CONN", "GATE", "3");
        ct.execute("TOG", "1", null);
        ct.execute("TOG", "3", null);
        ct.execute("TRIG", "1", null);
        assertTrue(raceType.getRuns().isEmpty());
        ct.execute("TRIG", "3", null);
        ct.execute("ENDRUN", null,null);
        assertFalse(raceType.getRuns().isEmpty());
        Iterator<Run> it = raceType.getRuns().iterator();
        for(Iterator<Run> itr = it; itr.hasNext();){
            assertTrue(itr.next().getResults().isEmpty());
        }
    }@Test
    public void test20(){
        ct.execute("POWER", null, null);
        ct.execute("CONN", "GATE", "1");
        ct.execute("CONN", "GATE", "3");
        ct.execute("EVENT", "IND", null);
        ct.execute("NEWRUN", null, null);
        getChannels();
        getRunCalled();
        getRacetype();
        ct.execute("START", null,null);
        assertTrue(runCalled);
        assertFalse(channels[0].isReady());
        assertTrue(raceType.getRuns().isEmpty());
        ct.execute("NUM ", "111", null);
        ct.execute("START", null, null);
        assertFalse(channels[0].isReady());
        assertTrue(raceType.getRuns().isEmpty());
        ct.execute("TRIG 3", null, null);
        ct.execute("ENDRUN", null,null);
        assertFalse(channels[0].isReady());
        assertFalse(raceType.getRuns().isEmpty());
    }
  /*  @Test
    public void test21(){
        ct.execute("power", null,null);
        ct.execute("event",null,null);
        ct.execute("newrun",null,null);
        ct.execute("num","4",null);
        ct.execute("tog","1",null);
        ct.execute("tog","2",null);
        ct.execute("0","trig","1",null);
        ct.execute("finish","2",null);


    }
*/
    private void getState(){
        try {
            m = ct.getClass().getDeclaredMethod("getState");
            m.setAccessible(true);
            state = (String) m.invoke(ct);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void getRunCalled(){
        try {
            runField = ct.getClass().getDeclaredField("runCalled");
            runField.setAccessible(true);
            runCalled = runField.getBoolean(ct);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void getEventCalled(){
        try {
            eventField = ct.getClass().getDeclaredField("eventCalled");
            eventField.setAccessible(true);
            eventCalled = eventField.getBoolean(ct);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void getChannels(){
        try {
            channelField = ct.getClass().getDeclaredField("channels");
            channelField.setAccessible(true);
            channels = (Channel[])channelField.get(ct);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void getRacetype(){
    	try{
    		eventField = ct.getClass().getDeclaredField("event");
    		eventField.setAccessible(true);
    		event = (Event)eventField.get(ct);
    		raceTypeField = event.getClass().getDeclaredField("_racetype");
    		raceTypeField.setAccessible(true);
    		raceType = (RaceType)raceTypeField.get(event);

		}catch(Exception e){
    		e.printStackTrace();
		}
	}
}
