package TimingSystem.RaceTypes;

import TimingSystem.Racer;
import TimingSystem.Result;
import TimingSystem.Run;
import Util.Time;

import java.util.*;

public class PARGRP implements RaceType {
    private Deque<Racer> _racers;

    private HashMap<Integer, Racer> _racerMap;
    private Deque<Racer> _finished;

    private ArrayList<Run> runs;

    private boolean inProg;


    /**
     * Constructor for Parallel Group RaceType
     */
    public PARGRP(){
        _racers = new LinkedList<>();
        _racerMap = new HashMap<>();
        _finished = new LinkedList<>();
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

        for(int i = 0; i < _racerMap.size(); ++i){
            if(bibNumber == _racerMap.get(i).getBibNumber())
                return false;
        }

        return true;
    }

    /**
     *
     * @param time
     * @param channelNum
     */
    @Override
    public void setTime(long time, int channelNum) {
        if(channelNum == 1 && !inProg){
            inProg = true;
            int i = 1;
            while(!_racers.isEmpty()){
                if(_racers.peek().getStartTime() == -1) {
                    Racer r = _racers.removeFirst();
                    r.setStartTime(time);
                    _racerMap.put(i, r);
                    ++i;
                }
            }
        } else {
            if(_racerMap.get(channelNum) != null) {
            Racer r = _racerMap.get(channelNum);
            r.setFinishTime(time);
            _finished.add(r);
            _racerMap.remove(channelNum);
            }
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
     * @param bibNumber
     */
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
        this.dnf();
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

    /**
     *
     * @param runNumber
     * @return
     */
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

    /**
     *
     * @param type
     * @return
     */
    @Override
    public String getData(String type) {
        String data = "";
        switch(type){
            case "queue":
                return listToString(_racers, false, true);
            case "running":
                return mapToString();
            case "finished":
                return listToString(_finished, true, false);
            default:
        }
        return data;
    }

    /**
     *
     * @return
     */
    @Override
    public Run getLastRun() {
        return runs.get(runs.size()-1);
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<Run> getRuns() {
        return runs;
    }

    /**
     *
     */
    @Override
    public void dnf() {
        Set<Integer> keys = _racerMap.keySet();
        for(int key : keys){
            Racer r  = _racerMap.remove(key);
            r.setFinishTime(-1);
            r.setStartTime(-1);
            _finished.add(r);
        }
    }

    /**
     *
     * @param list
     * @param finished
     * @param q
     * @return
     */
    private String listToString(Deque<Racer> list, boolean finished, boolean q) {
        long cTime = System.currentTimeMillis();
        String s = "";
        for(Racer r : list){
            s += r.getBibNumber() + ": ";
            if(!q){
                if (finished)
                    s += Time.getElapsed(r.getStartTime(), r.getFinishTime());
                else
                    s += Time.getElapsed(r.getStartTime(), cTime);
            }
            s += "\n";
        }
        return s;
    }

    /**
     *
     * @return
     */
    private String mapToString(){
        long cTime = System.currentTimeMillis();
        Set<Integer> keys = _racerMap.keySet();
        String s = "";
        for(Integer key : keys){
            Racer r = _racerMap.get(key);
            s += r.getBibNumber() + ": ";
            s += Time.getElapsed(r.getStartTime(), cTime);
            s += "\n";
        }
        return s;
    }
}



