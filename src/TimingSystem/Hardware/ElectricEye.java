package TimingSystem.Hardware;

public class ElectricEye extends Sensor {


    public ElectricEye(){

    }

    @Override
    long trigger() {
        return System.currentTimeMillis();
    }
}
