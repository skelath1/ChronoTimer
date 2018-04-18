package TimingSystem.Hardware;

public class ElectricEye extends Sensor {
    private Channel _channel;

    public ElectricEye(Channel channel){
        _channel = channel;
    }

    @Override
    long trigger() {
        return System.currentTimeMillis();
    }
}
