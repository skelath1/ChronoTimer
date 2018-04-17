package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Run;
import Util.Time;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class IND implements RaceType {
    private Channel[] _channels;

    // Will be used to return the racers
    //private Queue<TimingSystem.Racer> _racers;
    private Deque<Racer> _racers;

    private ArrayList<Run> runs;

    private Deque<Racer> _finished;

    // WIll be a linked list
    private Deque<Racer> _racerQueue;

    public IND(Channel[] channels){
        _racers = new LinkedList<>();
        runs = new ArrayList<Run>();
        _channels = channels;
        _racerQueue = new LinkedList<>();
        _finished = new LinkedList<>();
    }

    /**
     * Creates new TimingSystem.Racer and adds it to the TimingSystem.Racer Queue
     * @param bibNumber: bib number of racer
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
     *  Get/Removes first TimingSystem.Racer from _racers, then sets start time and
     *  Adds the racer to _racerQueue
     * @param startTime
     */
    @Override
    public void setStartTime(long startTime, int channelNum) {
        if(channelNum%2 == 0) return;
        if(!_racers.isEmpty()){
            Racer r = _racers.remove();
            r.setStartTime(startTime);
            _racerQueue.add(r);
        }
    }

    /**
     * Sets the finish time of the racer
     * @param finishTime
     */
    @Override
    public void setFinishTime(long finishTime, int channelNum) {
        if(channelNum%2 == 1) return;
        if(!_racerQueue.isEmpty()){
            if(_racerQueue.peek().getStartTime() != -1) {
                Racer r = _racerQueue.remove();
                r.setFinishTime(finishTime);
                _finished.add(r);
            }
        }
    }

    /**
     * Cancels racer and adds the racer back to start of queue
     */
    @Override
    public void cancelRacer() {
        Racer r = _racerQueue.removeLast();
        r.setStartTime(-1);
        r.setFinishTime(-1);
        _racers.addFirst(r);
    }

    /**
     * Clears the queues
     */
    @Override
    public void clear() {
        _racerQueue.clear();
        _racers.clear();
        _finished.clear();
    }

    /**
     *
     */
    @Override
    public void swap() {
        if(_racerQueue.size() >= 2) {
            Racer r1 = _racerQueue.removeFirst();
            Racer r2 = _racerQueue.removeFirst();
            _racerQueue.addFirst(r2);
            _racerQueue.addFirst(r1);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public void saveRun(){
        Run r = new Run(this.toString());
        r.addResults(_finished);
        runs.add(r);
    }

    /**
     *
     * @return
     */
    @Override
    public String printResults() {
        String s = "";

        //changed from racer to racerQueue
        for(Racer r : _finished) {
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
        return "IND";
    }

    @Override
    public String getData(String type) {
        String data = "";
        switch(type){
            case "queue":
                return listToString(_racers, false, false);
            case "running":
                return listToString(_racerQueue, true, false);

            case "finished":
                return listToString(_finished, false, true);

            default:
        }
        return data;
    }

    private String listToString(Deque<Racer> e, boolean running,  boolean finished){
        String data = "";
        long cTime = System.currentTimeMillis();
        for(Racer r : e){
            data += r.getBibNumber() + ": ";
            if(running)
                data += Time.getElapsed(r.getStartTime(), cTime);
            if(finished)
                data += Time.getElapsed(r.getStartTime(), r.getFinishTime());
            data += " \n";
        }

        return data;
    }
}
