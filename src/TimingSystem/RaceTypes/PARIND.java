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
    private ArrayList<Run> runs;

    private Deque<Racer> _finished;
    private Deque<Racer> _racersL;
    private Deque<Racer> _racersR;

    private Deque<Racer> _left;
    private Deque<Racer> _right;

    private boolean isRight = false;
    private boolean addRight = false;
    private boolean inProg;

    /**
     * Constructor for Parallel Individual Racetype
     */
    public PARIND(){
        _finished = new LinkedList<>();
        _racersL = new LinkedList<>();
        _racersR = new LinkedList<>();
        _left = new LinkedList<>();
        _right = new LinkedList<>();
        runs = new ArrayList<>();
    }


    /**
     * Creates new TimingSystem.Racer and adds it to the TimingSystem.Racer Queue
     * @param bibNumber : bib number of racer
     */
    @Override
    public void addRacer(int bibNumber) {
        if(!validNewRacer(bibNumber)) return;
        if(!addRight){
            _racersL.add(new Racer(bibNumber));
            addRight = !addRight;

        } else{
            _racersR.add(new Racer(bibNumber));
            addRight = !addRight;
        }
    }

    /**
     * Checks whether the racer is already in the event
     * @param bibNumber : Racer to check
     * @return : If racer is in the event
     */
    private boolean validNewRacer(int bibNumber){
        for(Racer r : _racersL){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _left){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _racersR){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _right){
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
        switch(channelNum){
            case 1:
                inProg = true;
                if(_racersL.isEmpty()) return;
                Racer ls = _racersL.removeFirst();
                ls.setStartTime(time);
                _left.add(ls);
                isRight = true;
                break;
            case 2:
                if(!_left.isEmpty()){
                    Racer lf = _left.removeFirst();
                    lf.setFinishTime(time);
                    _finished.add(lf);
                    isRight = true;
                    if(_left.isEmpty() && _right.isEmpty())
                        inProg = false;
                }
                break;
            case 3:
                inProg = true;
                if(_racersR.isEmpty()) return;
                Racer rs = _racersR.removeFirst();
                rs.setStartTime(time);
                _right.add(rs);
                isRight = false;
                break;
            case 4:
                if(!_right.isEmpty()){
                    Racer rf = _right.removeFirst();
                    rf.setFinishTime(time);
                    _finished.add(rf);
                    isRight = false;
                    if(_left.isEmpty() && _right.isEmpty())
                        inProg = false;
                }
                break;
            default:

        }
    }

    /**
     * Cancels racer and adds the racer back to start of queue
     */
    @Override
    public void cancelRacer() {
        // Don't know racers position
        if(isRight){
            Racer l = _left.removeFirst();
            l.setStartTime(-1);
            _racersL.addFirst(l);
        } else{
            Racer r = _right.removeFirst();
            r.setStartTime(-1);
            _racersR.addFirst(r);
        }
    }

    /**
     * Clears the queues
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
        inProg = false;
    }

    /**
     * Clears a specific racer from the queues depending on the bibNumber
     * @param bibNumber : bibNumber of the racer to clear
     */
    @Override
    public void clear(int bibNumber) {
        for(Racer r : _racersL){
            if(r.getBibNumber() == bibNumber) {
                _racersL.remove(r);
                break;
            }
        }

        for(Racer r : _racersR){
            if(r.getBibNumber() == bibNumber) {
                _racersR.remove(r);
                break;
            }
        }

        for(Racer r : _left){
            if(r.getBibNumber() == bibNumber) {
                _left.remove(r);
                break;
            }
        }

        for(Racer r : _right){
            if(r.getBibNumber() == bibNumber) {
                _right.remove(r);
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
     * Does nothing for this RaceType
     */
    @Override
    public void swap() {
        //Not sure if there is anything for this
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

        if(isRight){
            if(_left.isEmpty()) return;
            Racer l = _left.removeFirst();
            l.setStartTime(-1);
            l.setStartTime(-1);
            _finished.addFirst(l);
        } else{
            if(_right.isEmpty()) return;
            Racer r = _right.removeFirst();
            r.setStartTime(-1);
            r.setStartTime(-1);
            _finished.addFirst(r);
        }
    }

    /**
     * Saves the run in runs to be used in export
     */
    @Override
    public void saveRun(){
        Run r = new Run(this.toString());
        r.addResults(_racersL);
        r.addResults(_racersR);
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
        if(inProg){
            for(Racer r : _racersL){
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) + "\n";
            }
            for(Racer r : _racersR){
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) + "\n";
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
        return "PARIND";
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

    /**
     * Converts a list to a string
     * @param list1 : First list to convert
     * @param list2 : Second list to convert or null
     * @param finished : If the racers are finished
     * @return : Converted List(s) in string format
     */
    private String listToString(Deque<Racer> list1, Deque<Racer> list2, boolean finished){
        String s = "";
        for(Racer r : list1) {
            if(finished)
                s += r.getBibNumber() + ": " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) + " \n";
            else
                s += r.getBibNumber() + "\n";
        }
        if(list2 != null) {
            for (Racer r : list2) {
                if (finished)
                    s += r.getBibNumber() + ": " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) + " \n";
                else
                    s += r.getBibNumber() + "\n";
            }
        }
        return s;
    }
}
