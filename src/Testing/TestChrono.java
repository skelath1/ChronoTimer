package Testing;
import TimingSystem.ChronoTimer;
import org.junit.*;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class TestChrono {
    ChronoTimer ct;
    Method m;
    String state;

    @Before
    public void setUp(){
        ct = new ChronoTimer();
    }

    @Test
    public void testState(){
        getState();
        assertTrue(state.equalsIgnoreCase("OFF"));

        ct.execute("POWER", null);
        getState();
        assertTrue(state.equalsIgnoreCase("ON"));

        ct.execute("EVENT", "IND");
        getState();
        assertTrue(state.equalsIgnoreCase("EVENT"));

        ct.execute("NEWRUN", null);
        getState();

        ct.execute("TOG", "1");
        getState();
        assertTrue(state.equalsIgnoreCase("EVENT"));

        ct.execute("POWER",null);
        getState();
        assertTrue("Incorrect State: " + state.toString(),state.equalsIgnoreCase("OFF"));

        ct.execute("POWER",null);
        getState();
        assertTrue(state.equalsIgnoreCase("ON"));


        ct.execute("NEWRUN",null);
        getState();
        assertTrue(state.equalsIgnoreCase("ON"));

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
}
