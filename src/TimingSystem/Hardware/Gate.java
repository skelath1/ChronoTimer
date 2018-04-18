package TimingSystem.Hardware;

public class Gate extends Sensor {
    private enum STATE{
        OPEN, CLOSED
    }

    private STATE _state;

    public Gate(){

    _state = STATE.CLOSED;
    }

    @Override
    public long trigger() {
        return System.currentTimeMillis();
    }
}
