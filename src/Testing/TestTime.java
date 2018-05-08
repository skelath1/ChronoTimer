package Testing;
import Util.Time;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTime {
    Time t;
    @Before
    public void setup(){
        t = new Time();
    }

    @Test
    public void testGetElapsed(){
        assertEquals("Times are equal", "00:00:05.00", Time.getElapsed(5000, 10000));
        assertTrue("0 difference", Time.getElapsed(0,0).equals( "00:00:00.00"));
        //assertEquals("Greater than 99 Hrs","ERR", Time.getElapsed(0, 360000000));
    }

    @Test
    public void testStringToMilli(){
        assertTrue("5000 == 00:00:05.00", 5000 == Time.stringToMilliseconds("00:00:05.00"));
    }

    @Test
    public void testSplitComponents() {
        String[] strings = null;
        try {
            Method method = Time.class.getDeclaredMethod("splitComponents", String.class);
            method.setAccessible(true);
            strings = (String[]) method.invoke(method, "12:34:56.78");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue("12 == 12", strings[0].equals("12"));
        assertTrue("34 == 34", strings[1].equals("34"));
        assertTrue("56 == 56", strings[2].equals("56"));
        assertTrue("78 == 78", strings[3].equals("78"));
    }
}
