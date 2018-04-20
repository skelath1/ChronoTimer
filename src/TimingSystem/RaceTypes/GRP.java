package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Result;
import TimingSystem.Run;
import Util.Time;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class GRP implements RaceType{
    private Deque<Racer> _racers;
    private Deque<Racer> _racerQueue;
    private Deque<Racer> _finished;
    private ArrayList<Run> runs;
    private boolean inProg;
    private int anonBib = 99901;


    private Channel[] _channels;

    public GRP(Channel[] channels){
        _channels = channels;
        _racers = new LinkedList<>();
        _racerQueue = new LinkedList<>();
        _finished = new LinkedList<>();
        runs = new ArrayList<>();
    }
    @Override
    public ArrayList<Run> getRuns() {
        return runs;
    }

    /**
     * Adds a Racer to the Collection
     * @param bibNumber: Racer bib number
     */
    @Override
    public void addRacer(int bibNumber) {
        //TODO loop through all the racers in every list and if there is a temp bib replace with bibnumber
        boolean newR = true;
        for(Racer r : _finished){
            String b = r.getBibNumber() + "";
            if(b.length() > 3 && b.substring(0,3).equalsIgnoreCase("999")){
                r.setBibNumber(bibNumber);
                newR = false;
                break;
            }
        }
        if(newR == true){
            for(Racer r : _racerQueue){
                String b = r.getBibNumber()+"";
                if(b.length() > 3 && b.substring(0,3).equalsIgnoreCase("999")){
                    r.setBibNumber(bibNumber);
                    newR = false;
                    break;
                }
            }
            if(newR == true) {
                if (validNewRacer(bibNumber))
                    _racers.add(new Racer(bibNumber));
            }
        }

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
     * Sets the start times of the Racers in the Collection
     * @param startTime: Starting time
     * @param channelNum: Channel Triggered
     */
    @Override
    public void setStartTime(long startTime, int channelNum) {
        if (channelNum == 1) {
            inProg = true;
            if (_racers.isEmpty()) {
                Racer r = new Racer(anonBib);
                r.setStartTime(startTime);
                _racerQueue.add(r);
                ++anonBib;
            } else {
                while (!_racers.isEmpty()) {
                    if (_racers.peek().getStartTime() != -1) return;
                    Racer r = _racers.removeFirst();
                    r.setStartTime(startTime);
                    _racerQueue.add(r);
                }

            }
        }
    }



    /**
     * Sets the finish time of A Racer from the Channel Triggered
     * @param finishTime: Finishing Time
     * @param channelNum: Channel Triggered
     */
    @Override
    public void setFinishTime(long finishTime, int channelNum) {
        if(channelNum == 2){
            if(_racerQueue.isEmpty()) return;
            Racer r = _racerQueue.removeFirst();
            r.setFinishTime(finishTime);
            _finished.add(r);
            if(_racerQueue.isEmpty() && _racers.isEmpty())
                inProg = false;
        }
    }

    /**
     * Cancels the Racer
     */
    @Override
    public void cancelRacer() {
        // Not sure how this would work
    }

    /**
     * Clears the Collections
     */
    @Override
    public void clear() {
        _racers.clear();
        _racerQueue.clear();
        _finished.clear();
        anonBib = 99901;
        inProg = false;
    }

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
     * Swaps the position of the first two Racers to finish
     */
    @Override
    public void swap() {
//        if(_racerQueue.size() >= 2) {
//            Racer r1 = _racerQueue.removeFirst();
//            Racer r2 = _racerQueue.removeFirst();
//            _racerQueue.addFirst(r2);
//            _racerQueue.addFirst(r1);
//        }
    }

    /**
     * Saves the run results
     * @return results as Run
     */
    @Override
    public void saveRun(){
        Run r = new Run(this.toString());
        r.addResults(_finished);
        runs.add(r);
    }

    /**
     * Puts the results from the race in a readable format
     * @return
     */
    @Override
    public String printResults() {
        String s = "";
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
     * return the RaceType
     * @return
     */
    @Override
    public String toString(){
        return "GRP";
    }

    @Override
    public String getData(String type) {
        String data = "";
        switch(type){
            case "queue":
                return listToString(_racers, false, true);
            case "running":
                return listToString(_racerQueue, false, false);
            case "finished":
                return listToString(_finished, true, false);
            default:
        }
        return data;
    }

    private String listToString(Deque<Racer> list, boolean finished, boolean q) {
        long cTime = System.currentTimeMillis();
        String s = "";
        for(Racer r : list){
            s += r.getBibNumber() + " ";
            if(!q) {
                if (finished)
                    s += Time.getElapsed(r.getStartTime(), r.getFinishTime());
                else
                    s += Time.getElapsed(r.getStartTime(), cTime);
            }
            s += "\n";
        }
        return s;
    }
}
