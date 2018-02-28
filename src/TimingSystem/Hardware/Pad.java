package TimingSystem.Hardware;

public class Pad extends Sensor {
    private Channel _channel;

    public Pad(Channel channel){
        _channel = channel;
    }

    @Override
    void trigger() {

    }
}
