package Testing;
import TimingSystem.Hardware.Channel;
import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestChannel {
    Channel c1 = new Channel(1);

    @Test
    public void test1()
    {
        assertFalse("Channel should be off by default",c1.isReady());
        c1.toggle();
        assertTrue("Channel should be on after toggled",c1.isReady());
    }
}

