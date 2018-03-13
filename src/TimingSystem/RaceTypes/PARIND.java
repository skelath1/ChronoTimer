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

    boolean isRight = false;


    public PARIND(Channel[] channels){
        _channels = channels;
        _racers = new LinkedList<>();
        _left = new LinkedList<>();
        _right = new LinkedList<>();
    }

    /**
     *
     * @param bibNumber
     */
    @Override
    public void addRacer(int bibNumber) {
        _racers.add(new Racer(bibNumber));
    }

    /**
     *
     * @param startTime
     * @param channelNum
     */
    @Override
    public void setStartTime(long startTime, int channelNum) {
        if(_racers.isEmpty() || _racers.peek().getStartTime() != -1) return;
        if(channelNum == 1){
            Racer l = _racers.removeFirst();
            l.setStartTime(startTime);
            _left.add(l);
        } else if(channelNum == 3){
            Racer r = _racers.removeFirst();
            r.setStartTime(startTime);
            _right.add(r);
        }
    }

    /**
     *
     * @param finishTime
     * @param channelNum
     */
    @Override
    public void setFinishTime(long finishTime, int channelNum) {
        if(channelNum == 2){
            if(!_left.isEmpty()){
                if(_left.peek().getStartTime() != -1){
                    Racer l = _left.removeFirst();
                    l.setFinishTime(finishTime);
                    _racers.add(l);
                    isRight = true;
                }
            }
        } else if(channelNum == 4){
            if(!_right.isEmpty()){
                if(_right.peek().getStartTime() != -1) {
                    Racer r = _right.remove();
                    r.setFinishTime(finishTime);
                    _racers.add(r);
                    isRight = false;
                }
            }
        }
    }

    /**
     *
     */
    @Override
    public void cancelRacer() {
        // Don't know racers position
        if(!isRight){
            Racer l = _left.removeFirst();
            l.setStartTime(-1);
            _racers.addFirst(l);
        } else{
            Racer r = _left.removeFirst();
            r.setStartTime(-1);
            _racers.addFirst(r);
        }
    }

    /**
     *
     */
    @Override
    public void clear() {
        _racers.clear();
        _left.clear();
        _right.clear();
        isRight = false;
    }

    /**
     *
     */
    @Override
    public void swap() {
//        ???
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
        return "PARIND";
    }
}
