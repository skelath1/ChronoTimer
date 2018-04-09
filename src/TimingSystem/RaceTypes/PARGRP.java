package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Run;
import Util.Time;

import java.util.*;

public class PARGRP implements RaceType {
    private Deque<Racer> _racers;

    private HashMap<Integer, Racer> _racerMap;

    private Channel[] _channels;

    private ArrayList<Run> runs;


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
        if(validNewRacer(bibNumber))
            _racers.add(new Racer(bibNumber));
    }
    /**
     *
     * @param bibNumber
     * @return
     */
    private boolean validNewRacer(int bibNumber){
        for(Racer r : _racers){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        return true;
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
    public void saveRun(){
        Run r = new Run(this.toString());
        r.addResults(_racers);
        runs.add(r);
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

    @Override
    public String printResults(int runNumber) {
        return null;
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
