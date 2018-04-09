package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Run;
import Util.Time;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class GRP implements RaceType{
    private Deque<Racer> _racers;
    private Deque<Racer> _racerQueue;
    private ArrayList<Run> runs;


    private Channel[] _channels;

    public GRP(Channel[] channels){
        _channels = channels;
        _racers = new LinkedList<>();
        _racerQueue = new LinkedList<>();
    }

    /**
     * Adds a Racer to the Collection
     * @param bibNumber: Racer bib number
     */
    @Override
    public void addRacer(int bibNumber) {
        _racers.add(new Racer(bibNumber));
    }

    /**
     * Sets the start times of the Racers in the Collection
     * @param startTime: Starting time
     * @param channelNum: Channel Triggered
     */
    @Override
    public void setStartTime(long startTime, int channelNum) {
        if(channelNum == 1){
            while(!_racers.isEmpty()){
                if(_racers.peek().getStartTime() != -1) return;
                Racer r = _racers.removeFirst();
                r.setStartTime(startTime);
                _racerQueue.add(r);
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
            _racers.add(r);
        }
    }

    /**
     * Cancels the Racer
     */
    @Override
    public void cancelRacer() {

    }

    /**
     * Clears the Collections
     */
    @Override
    public void clear() {
        _racers.clear();
        _racerQueue.clear();
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
        r.addResults(_racers);
        runs.add(r);
    }

    /**
     * Puts the results from the race in a readable format
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
     * return the RaceType
     * @return
     */
    @Override
    public String toString(){
        return "GRP";
    }
}
