public class Gate extends Sensor {
    private enum STATE{
        ON, OFF
    }
    private Channel _channel;
    private STATE _state;

    public Gate(Channel channel){
    _channel = channel;
    _state = STATE.OFF;
    }

    @Override
    void trigger() {
       if(_channel.getState()){
           if(_state == STATE.OFF){

           } else{

           }
       }
    }
}
