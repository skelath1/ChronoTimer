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

    public boolean getState(){
        if(_state == STATE.ON)
            return true;
        return false;
    }
}
