package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Run;
import Util.Time;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class PARGRP implements RaceType {
    private Deque<Racer> _racers;

    private HashMap<Integer, Racer> _racerMap;

    private Channel[] _channels;

    public PARGRP(Channel[] channels){
        _racers = new LinkedList<>();
        _racerMap = new HashMap<>();

        _channels = channels;
    }

    /**
     *
     * @param bibNumber
     */
    @Override
    public void addRacer(int bibNumber) {
        _racers.add(new Racer(bibNumber));
    }

    /**
     *
     * @param startTime
     * @param channelNum
     */
    @Override
    public void setStartTime(long startTime, int channelNum) {
        if(channelNum == 1){
            int i = 1;
            while(!_racers.isEmpty()){
                if(_racers.peek().getStartTime() == -1) {
                    Racer r = _racers.removeFirst();
                    r.setStartTime(startTime);
                    _racerMap.put(i, r);
                    ++i;
                }
            }
        }
    }

    /**
     *
     * @param finishTime
     * @param channelNum
     */
    @Override
    public void setFinishTime(long finishTime, int channelNum) {
        if(_racerMap.get(channelNum) != null) {
            Racer r = _racerMap.get(channelNum);
            r.setFinishTime(finishTime);
            _racers.add(r);
            _racerMap.remove(channelNum);

        }
    }

    /**
     *
     */
    @Override
    public void cancelRacer() {
// ???
    }

    /**
     *
     */
    @Override
    public void clear() {
        _racers.clear();
        _racerMap.clear();
    }

    /**
     *
     */
    @Override
    public void swap() {

    }

    /**
     *
     * @return
     */
    @Override
    public Run saveRun(){
        Run r = new Run(this.toString());
        r.addResults(_racers);
        return r;
    }

    /**
     *
     * @return
     */
    @Override
    public String printResults() {
        String s = "";
        for(Racer r : _racers) {
            if(r.getFinishTime() == -1)
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + "DNF\n";
            else
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) + "\n";
        }
        return s;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return "PARGRP";
    }
}
