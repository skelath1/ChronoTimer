package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Result;
import TimingSystem.Run;
import Util.Time;

import java.util.*;

public class PARGRP implements RaceType {
    private Deque<Racer> _racers;

    private HashMap<Integer, Racer> _racerMap;

    private Channel[] _channels;

    private ArrayList<Run> runs;

    private boolean inProg;


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
            inProg = true;
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

    @Override
    public void clear(int bibNumber) {

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
        long t = System.currentTimeMillis();
        if(inProg){
            for(Racer r : _racers){
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) + "\n";
            }
        } else {
            Run r = runs.get(runs.size()-1);
            for(Result res : r.getResults()){
                s += "TimingSystem.Racer: " + res.get_bib() + " : " + res.get_time() + "\n";
            }
        }
        return s;
    }

    @Override
    public String printResults(int runNumber) {
        String s = "";
        Run r = runs.get(runNumber-1);
        for(Result res : r.getResults()){
            s += "TimingSystem.Racer: " + res.get_bib() + " : " + res.get_time() + "\n";
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

    @Override
    public String getData(String type) {
        String data = "";
        switch(type){
            case "queue":

                break;
            case "running":

                break;
            case "finished":

                break;
            default:
        }
        return data;
    }

    private String listToString(){
        return null;
    }
}



