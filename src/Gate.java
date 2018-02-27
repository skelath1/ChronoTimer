public class Gate extends Sensor {
    private enum STATE{
        OPEN, CLOSED
    }
    private Channel _channel;
    private STATE _state;

    public Gate(Channel channel){
    _channel = channel;
    _state = STATE.CLOSED;
    }

    @Override
    public void trigger() {

    }
}
