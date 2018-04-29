package Testing;
import TimingSystem.ChronoTimer;
import org.junit.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestChrono {
    @Test
    public void test(){
        assertEquals(1, 1);
    }
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
    public void testOrder(){

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
            state = (String) m.invoke(ct);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void getEventCalled(){
        try {
            eventField = ct.getClass().getDeclaredField("eventCalled");
            eventField.setAccessible(true);
            state = (String) m.invoke(ct);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
