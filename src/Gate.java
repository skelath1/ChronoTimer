public class Gate extends Sensor {
    private Channel _channel;

    public Gate(Channel channel){
    _channel = channel;
    }

    @Override
    void trigger() {

    }
}
