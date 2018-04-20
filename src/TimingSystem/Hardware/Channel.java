package TimingSystem.Hardware;

public class Channel {
    private enum STATE{
        ON, OFF
    }
    private int _channelNum;
    private STATE _state;
    private Sensor _sensor;

    public Channel(int channelNum){
        _state = STATE.OFF;
        _channelNum = channelNum;
    }

    public void connectSensor(Sensor sensor){
        _sensor = sensor;
    }

    public void toggle(){
        if(_state == STATE.ON)
            _state = STATE.OFF;
        else
            _state = STATE.ON;
    }

    public boolean isReady(){
        if(_state == STATE.ON && _sensor != null)
            return true;
        return false;
    }

    public long triggerSensor(){
        return _sensor.trigger();
    }

    public void setOff(){
        _state = STATE.OFF;
    }
}
