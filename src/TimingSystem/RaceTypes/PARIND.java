package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Result;
import TimingSystem.Run;
import Util.Time;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class PARIND implements RaceType {
    private Channel[] _channels;

    private ArrayList<Run> runs;

    private Deque<Racer> _finished;
    private Deque<Racer> _racersL;
    private Deque<Racer> _racersR;

    private Deque<Racer> _left;
    private Deque<Racer> _right;

    private boolean isRight = false;
    private boolean addRight = false;


    public PARIND(Channel[] channels){
        _channels = channels;
        _finished = new LinkedList<>();
        _racersL = new LinkedList<>();
        _racersR = new LinkedList<>();
        _left = new LinkedList<>();
        _right = new LinkedList<>();
    }

    /**
     *
     * @param bibNumber
     */
    @Override
    public void addRacer(int bibNumber) {
        validNewRacer(bibNumber);
        if(!addRight){
            _racersL.addLast(new Racer(bibNumber));
            addRight = !addRight;
            printList(_racersL);

        } else{
            _racersR.addLast(new Racer(bibNumber));
            addRight = !addRight;
            printList(_racersR);

        }
    }

    private boolean validNewRacer(int bibNumber){
        for(Racer r : _racersL){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _racersR){
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
            if(_racersL.isEmpty()) return;
            Racer l = _racersL.removeFirst();
            l.setStartTime(startTime);
            _left.addLast(l);
            printList(_racersL);
        } else if(channelNum == 3){
            if(_racersR.isEmpty()) return;
            Racer r = _racersR.removeFirst();
            r.setStartTime(startTime);
            _right.addLast(r);
            printList(_racersR);
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
                Racer l = _left.removeFirst();
                l.setFinishTime(finishTime);
                _finished.add(l);
                isRight = true;

            }
        } else if(channelNum == 4){
            if(!_right.isEmpty()){
                Racer r = _right.removeFirst();
                r.setFinishTime(finishTime);
                _finished.add(r);
                isRight = false;
            }
        }
        printList(_finished);
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
            _racersL.addFirst(l);
        } else{
            Racer r = _left.removeFirst();
            r.setStartTime(-1);
            _racersR.addFirst(r);
        }
    }

    /**
     *
     */
    @Override
    public void clear() {
        _finished.clear();
        _racersL.clear();
        _racersR.clear();
        _left.clear();
        _right.clear();
        isRight = false;
        addRight = false;
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
        return "PARIND";
    }

    @Override
    public String getData(String type) {
        String data = "";
        switch(type){
            case "queue":
                    return listToString(_racersL, _racersR, false);
            case "running":
                String s = "";
                long cTime = System.currentTimeMillis();
                for(Racer r : _left) {
                    s += r.getBibNumber() + " " + Time.getElapsed(r.getStartTime(), cTime) + " \n";
                }
                for(Racer r : _right) {
                    s += r.getBibNumber() + " " + Time.getElapsed(r.getStartTime(), cTime) + " \n";
                }
                return s;
            case "finished":
                return listToString(_finished, null, true);

            default:
        }
        return data;
    }

    private String listToString(Deque<Racer> list1, Deque<Racer> list2, boolean finished){
        String s = "";
        for(Racer r : list1) {
            if(finished)
                s += r.getBibNumber() + " " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) + " \n";
            else
                s += r.getBibNumber() + "\n";
        }
        if(list2 != null) {
            for (Racer r : list2) {
                if (finished)
                    s += r.getBibNumber() + " " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) + " \n";
                else
                    s += r.getBibNumber() + "\n";
            }
        }
        return s;
    }

    private void printList(Deque<Racer> list){
        for(Racer r : list){
            System.out.println(r.getBibNumber());
        }
    }
}
