package Testing;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestChannel.class, TestChrono.class, TestEvent.class, TestRacer.class, TestSim.class, TestTime.class

})
public class TestSuite {

}