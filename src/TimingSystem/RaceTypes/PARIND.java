package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Run;
import Util.Time;

import java.util.Deque;
import java.util.LinkedList;

public class PARIND implements RaceType {
    private Channel[] _channels;
    private Deque<Racer> _racers;

    private Deque<Racer> _left;
    private Deque<Racer> _right;

    boolean _rightLane = false;
    boolean _leftLane = true;


    public PARIND(Channel[] channels){
        _channels = channels;
        _racers = new LinkedList<>();
        _left = new LinkedList<>();
        _right = new LinkedList<>();
    }

    @Override
    public void addRacer(int bibNumber) {
        _racers.add(new Racer(bibNumber));
    }

    @Override
    public void setStartTime(long startTime) {
        if(!_rightLane){
            Racer l = _racers.removeFirst();
            l.setStartTime(startTime);
            _left.add(l);
        } else{
            Racer r = _racers.removeFirst();
            r.setStartTime(startTime);
            _right.add(r);
        }
        _rightLane = !_rightLane;
    }

    @Override
    public void setFinishTime(long finishTime) {
        if(_leftLane){
            Racer l = _left.removeFirst();
            l.setFinishTime(finishTime);
            _racers.add(l);
        } else{
            Racer r = _right.removeFirst();
            r.setFinishTime(finishTime);
            _racers.add(r);
        }
        _leftLane = !_leftLane;
    }

    @Override
    public void cancelRacer() {
        // Don't know racers position
    }

    @Override
    public void clear() {
        _racers = new LinkedList<>();
        _left = new LinkedList<>();
        _right = new LinkedList<>();
        _rightLane = false;
        _leftLane = true;
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
    public String toString(){
        return "PARIND";
    }
}
