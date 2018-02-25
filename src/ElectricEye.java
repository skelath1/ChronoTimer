public class ElectricEye extends Sensor {
    private Channel _channel;

    public ElectricEye(Channel channel){
        _channel = channel;
    }

    @Override
    void trigger() {

    }
}
