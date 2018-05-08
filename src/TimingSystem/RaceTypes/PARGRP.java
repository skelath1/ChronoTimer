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
        runs = new ArrayList<>();
    }

    /**
     * Creates new TimingSystem.Racer and adds it to the TimingSystem.Racer Queue
     * @param bibNumber : bib number of racer
     */
    @Override
    public void addRacer(int bibNumber) {
        if(validNewRacer(bibNumber))
            _racers.add(new Racer(bibNumber));
    }
    /**
     * Checks whether the racer is already in the event
     * @param bibNumber : Racer to check
     * @return : If racer is in the event
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
     * Sets Start / Finish time for a racer depending on the channel number
     * @param time : Start / Finish time of racer
     * @param channelNum : Channel of sensor that was triggered
     */
    @Override
    public void setTime(long time, int channelNum) {
        if(channelNum == 1 && !inProg){
            inProg = true;
            int i = 1;
            while(!_racers.isEmpty()){
                if(_racers.peek().getStartTime() == 0) {
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
     * Cancels racer and adds the racer back to start of queue
     */
    @Override
    public void cancelRacer() {
        // ???
    }

    /**
     * Clears the queues
     */
    @Override
    public void clear() {
        _racers.clear();
        _racerMap.clear();
        _finished.clear();
        inProg = false;
    }

    /**
     * Clears a specific racer from the queues depending on the bibNumber
     * @param bibNumber : bibNumber of the racer to clear
     */
    @Override
    public void clear(int bibNumber) {
        for(Racer r : _racers){
            if(r.getBibNumber() == bibNumber){
                _racers.remove(r);
                return;
            }
        }

        for(int ind : _racerMap.keySet()){
            if(_racerMap.get(ind).getBibNumber() == bibNumber){
                _racerMap.remove(ind);
                return;
            }
        }

        for(Racer r : _finished){
            if(r.getBibNumber() == bibNumber){
                _finished.remove(r);
                return;
            }
        }
    }

    /**
     * Swaps next two racers to finish
     */
    @Override
    public void swap() {
        // Does Nothing
    }

    /**
     * Saves the run in runs to be used in export
     */
    @Override
    public void saveRun(){
        Run r = new Run(this.toString());
        r.addResults(_racers);
        this.dnf();
        r.addResults(_finished);
        runs.add(r);
    }

    /**
     * Puts the results of the race into a readable format
     * @return : String of the results of the race
     */
    @Override
    public String printResults() {
        String s = "";
        long t = System.currentTimeMillis();
//        if(inProg){
//            for(Racer r : _racers){
//                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) + "\n";
//            }
//        } else {
            Run r = runs.get(runs.size()-1);
            for(Result res : r.getResults()){
                s += "TimingSystem.Racer: " + res.get_bib() + " : " + res.get_time() + "\n";
            }
        //}
        return s;
    }

    /**
     * Puts the results of a specified run into a readable format
     * @param runNumber : Number of the run to get
     * @return : Readable version of results for the specified run
     */
    @Override
    public String printResults(int runNumber) {
        String s = "";
        if(runs.size() <= runNumber-1) return s;
        Run r = runs.get(runNumber-1);
        for(Result res : r.getResults()){
            s += "TimingSystem.Racer: " + res.get_bib() + " : " + res.get_time() + "\n";
        }
        return s;
    }

    /**
     * returns the type of the Race as a String
     * @return the type of the Race
     */
    @Override
    public String toString(){
        return "PARGRP";
    }

    /**
     * Grabs data for specified list
     * @param type : Place to get data from
     * @return : Readable version of data
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
     * Grabs the last run in the list of runs
     * @return : Last run
     */
    @Override
    public Run getLastRun() {
        return runs.get(runs.size()-1);
    }

    /**
     * Grabs all the runs in the event
     * @return : List of runs
     */
    @Override
    public ArrayList<Run> getRuns() {
        return runs;
    }

    /**
     * Sets all in progress racers to dnf
     */
    @Override
    public void dnf() {
        if(_racerMap.isEmpty()) return;
        Set<Integer> keys = _racerMap.keySet();
        Iterator<HashMap.Entry<Integer, Racer>> it = _racerMap.entrySet().iterator();
        while( it.hasNext()){
            HashMap.Entry<Integer, Racer> entry = it.next();
            Racer r = entry.getValue();
            r.setFinishTime(-1);
            r.setStartTime(-1);
            _finished.add(r);
            it.remove();
        }
    }

    /**
     * Helper method to put specified list in a readable format
     * @param list : list to be formatted
     * @param finished : boolean of if list is the finished list
     * @param q : boolean if if the list is the queue list
     * @return : String of list in readable format
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
     * Converts a Map to a readable format
     * @return : Map data as String
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



