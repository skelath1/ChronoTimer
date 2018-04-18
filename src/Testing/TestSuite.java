package Testing;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestChannel.class, TestChrono.class, TestINDRace.class, TestRacer.class, TestSim.class, TestTime.class, TestPARINDRace.class, TestGRPRace.class

})
public class TestSuite {

}