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

        ct.execute("TOG", "1");
        getState();
        assertTrue(state.equalsIgnoreCase("TOG"));

        ct.execute("TOG", "2");
        getState();
        assertTrue(state.equalsIgnoreCase("INPUTRACERS"));

        ct.execute("NUM", "123");
        getState();
        assertTrue(state.equalsIgnoreCase("INPUTRACERS"));

        ct.execute("TRIG", "1");
        getState();
        assertTrue(state.equalsIgnoreCase("INPROGRESS"));



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
