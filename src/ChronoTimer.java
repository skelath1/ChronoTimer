import Util.*;

public class ChronoTimer {
    private Channel[] _channels;
    private Event e;

    public ChronoTimer(){
        _channels = new Channel[8];
        for(int i = 0; i < 8; ++i){
            _channels[i] = new Channel(i+1);
        }
        e = new Event("IND", _channels);
    }

    public void execute(){

    }

    public Channel[] getChannels(){
        return _channels;
    }

    public void toggleChannel(int channelNum){
        _channels[channelNum+1].toggle();
    }
}
