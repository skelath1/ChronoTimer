package Testing;
import TimingSystem.ChronoTimer;
import TimingSystem.Hardware.Channel;
import org.junit.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        assertFalse(channels[0].isReady());

        //disconnect turn off and try to connect sensor

        ct.execute("POWER", null,null);
        ct.execute("CONN", "Gate","1");
        ct.execute("DISC", "1",null);
        getChannels();
        assertFalse(channels[0].isReady());


    }@Test
    public void test10(){

    }@Test
    public void test11(){

    }
    @Test
    public void test12(){

    }
    @Test
    public void test13(){

    }@Test
    public void test14(){

    }
    @Test
    public void test15(){

    }
    @Test
    public void test16(){

    }
    @Test
    public void test17(){

    }
    @Test
    public void test18(){

    }@Test
    public void test19(){

    }@Test
    public void test20(){

    }@Test
    public void test21(){

    }











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

}
