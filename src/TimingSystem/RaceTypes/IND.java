package TimingSystem.RaceTypes;

import TimingSystem.Racer;
import TimingSystem.Result;
import TimingSystem.Run;
import Util.Time;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class IND implements RaceType {
    private Deque<Racer> _racers;

    private ArrayList<Run> runs;

    private Deque<Racer> _finished;

    private Deque<Racer> _racerQueue;

    private boolean inProg;

    /**
     * Constructor for Individual RaceType
     */
    public IND(){
        _racers = new LinkedList<>();
        runs = new ArrayList<>();
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
        if(_racerQueue.isEmpty()) return;
        Racer r = _racerQueue.removeFirst();
        r.setStartTime(-1);
        r.setFinishTime(-1);
        _finished.add(r);
    }

    /**
     *
     * @param bibNumber
     * @return
     */
    private boolean validNewRacer(int bibNumber){
        for(Racer r : _racers){
            if(r.getBibNumber() ==   bibNumber)
                return false;
        }

        for(Racer r : _racerQueue){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _finished){
            if(r.getBibNumber() == bibNumber)
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
        if(channelNum%2 == 1){
            if(!_racers.isEmpty()){
                inProg = true;

                Racer r = _racers.remove();
                r.setStartTime(time);
                _racerQueue.add(r);
            }
        } else{
            if(!_racerQueue.isEmpty()){
                if(_racerQueue.peek().getStartTime() != -1) {
                    Racer r = _racerQueue.remove();
                    r.setFinishTime(time);
                    _finished.add(r);
                }
                if(_racerQueue.isEmpty())
                    inProg = false;
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
        inProg = false;
    }

    /**
     * Clears a specific racer from the queues depending on the bibNumber
     * @param bibNumber : bibNumber of the racer to clear
     */
    @Override
    public void clear(int bibNumber) {
        for(Racer r : _racers){
            if(r.getBibNumber() == bibNumber) {
                _racers.remove(r);
                break;
            }
        }

        for(Racer r : _racerQueue){
            if(r.getBibNumber() == bibNumber) {
                _racerQueue.remove(r);
                break;
            }
        }

        for(Racer r : _finished){
            if(r.getBibNumber() == bibNumber) {
                _finished.remove(r);
                break;
            }
        }

    }

    /**
     * Swaps next two racers to finish in the queue
     */
    @Override
    public void swap() {
        if(_racerQueue.size() >= 2) {
            Racer r1 = _racerQueue.removeFirst();
            Racer r2 = _racerQueue.removeFirst();
            _racerQueue.add(r2);
            _racerQueue.add(r1);
        }
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
     * Prints the results of the race to the console
     * @return String of the results of the race
     */
    @Override
    public String printResults() {
        String s = "";

        //changed from racer to racerQueue
        long t = System.currentTimeMillis();
        if(inProg){
            for(Racer r : _racerQueue){
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) +"\n";
            }
        } else {
            if(runs.isEmpty()){
                for(Racer r : _finished){
                    s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) +"\n";
                }
            }
            else {
                Run r = runs.get(runs.size() - 1);
                for (Result res : r.getResults()) {
                    s += "TimingSystem.Racer: " + res.get_bib() + " : " + res.get_time() + "\n";
                }
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
     * returns the type of the Race as a String
     * @return the type of the Race
     */
    @Override
    public String toString(){
        return "IND";
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
                return listToString(_racers, false, false);
            case "running":
                return listToString(_racerQueue, true, false);

            case "finished":
                return listToString(_finished, false, true);

            default:
        }
        return data;
    }

    /**
     *
     * @param e
     * @param running
     * @param finished
     * @return
     */
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
