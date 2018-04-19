package Testing;
import TimingSystem.Hardware.Channel;
import TimingSystem.Hardware.Gate;
import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestChannel {
    Channel c1 = new Channel(1);

    @Test
    public void testIsReady()
    {
        assertFalse("Channel should be off by default",c1.isReady());
        c1.toggle();
        assertFalse("Channel should be on after toggled and should have a sensor connected",c1.isReady());
        c1.connectSensor(new Gate());
        assertTrue("Channel should be toggeld on and should have sensor connected", c1.isReady());
    }
}

