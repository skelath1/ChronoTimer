package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Run;
import Util.Time;

import java.util.Deque;
import java.util.LinkedList;

public class IND implements RaceType {
    private Channel[] _channels;

    // Will be used to return the racers
    //private Queue<TimingSystem.Racer> _racers;
    private Deque<Racer> _racers;


    // WIll be a linked list
    private Deque<Racer> _racerQueue;

    public IND(Channel[] channels){
        _racers = new LinkedList<>();

        _channels = channels;
        _racerQueue = new LinkedList<>();
    }

    /**
     * Creates new TimingSystem.Racer and adds it to the TimingSystem.Racer Queue
     * @param bibNumber: bib number of racer
     */

    @Override
    public void addRacer(int bibNumber) {
        _racers.add(new Racer(bibNumber));
    }

    /**
     *  Get/Removes first TimingSystem.Racer from _racers, then sets start time and
     *  Adds the racer to _racerQueue
     * @param startTime
     */

    @Override
    public void setStartTime(long startTime) {
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
    public void setFinishTime(long finishTime) {
        if(!_racerQueue.isEmpty()){
            if(_racerQueue.peek().getStartTime() != -1) {
                Racer r = _racerQueue.remove();
                r.setFinishTime(finishTime);
                _racers.add(r);
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
        _racerQueue = new LinkedList<>();
        _racers = new LinkedList<>();
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
        return "IND";
    }
}
