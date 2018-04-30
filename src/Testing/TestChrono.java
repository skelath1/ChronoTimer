package Testing;
import TimingSystem.ChronoTimer;
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
    public void testOrder(){

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

}
