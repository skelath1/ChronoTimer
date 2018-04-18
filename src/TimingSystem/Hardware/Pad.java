package TimingSystem.Hardware;

public class Pad extends Sensor {


    public Pad(){

    }

    @Override
    long trigger() {
        return System.currentTimeMillis();
    }
}
