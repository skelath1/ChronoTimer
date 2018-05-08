package TimingSystem.RaceTypes;

import TimingSystem.Racer;
import TimingSystem.Result;
import TimingSystem.Run;
import Util.Time;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

public class GRP implements RaceType{
    private Deque<Racer> _racers;
    private Deque<Racer> _racerQueue;
    private Deque<Racer> _finished;
    private ArrayList<Run> runs;
    private boolean inProg;
    private int anonBib = 99901;
    private long sTime;

    /**
     * Constructor for Group Race
     */
    public GRP(){
        _racers = new LinkedList<>();
        _racerQueue = new LinkedList<>();
        _finished = new LinkedList<>();
        runs = new ArrayList<>();
    }

    /**
     * Gets the last run of the event
     * @return : Last Run of event
     */
    @Override
    public Run getLastRun() {
        return runs.get(runs.size()-1);
    }
    /**
     * Gets all the runs in the event
     * @return : ArrayList of runs from the event
     */
    @Override
    public ArrayList<Run> getRuns() {
        return runs;
    }

    /**
     * Sets the racers times to dnf if dnf command is given
     */
    @Override
    public void dnf() {
        for(int i = 0; i < _racerQueue.size(); ++i){
            Racer a = _racerQueue.removeFirst();
            a.setFinishTime(-1);
            a.setStartTime(-1);
            _finished.add(a);
        }
    }

    /**
     * Adds a Racer to the racer list or assigns a Bib number to a racer
     * already in the race
     * @param bibNumber: Racer bib number
     */
    @Override
    public void addRacer(int bibNumber) {
        boolean newR = true;
        for(Racer r : _finished){
            String b = r.getBibNumber() + "";
            if(b.length() > 3 && b.substring(0,3).equalsIgnoreCase("999")){
                if(validNewRacer(bibNumber)) {
                    r.setBibNumber(bibNumber);
                    newR = false;
                    break;
                }
            }
        }
        if(newR == true){
            if(validNewRacer(bibNumber)) {
                for (Racer r : _racerQueue) {
                    String b = r.getBibNumber() + "";
                    if (b.length() > 3 && b.substring(0, 3).equalsIgnoreCase("999")) {
                        r.setBibNumber(bibNumber);
                        newR = false;
                        break;
                    }
                }
            }
            if(newR == true) {
                if (validNewRacer(bibNumber))
                    _racers.add(new Racer(bibNumber));
            }
        }

    }

    /**
     * Validates that the racer is not already in the event
     * @param bibNumber : Bib Number to be checked
     * @return : whether the racer is in the event
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
     * Sets the start or finish time of the racer on the given channel
     * if channel 1 is passed and the race is not in progress,
     * all racers will have be started
     *
     * If the race is in Progress and channel 2 is passed while there
     * are no racers in the waiting queue then anonymous racers will
     * be started
     * @param time : start/finish time
     * @param channelNum : channel number triggered
     */
    @Override
    public void setTime(long time, int channelNum) {
        if (channelNum == 1) {
            if(!inProg){
                sTime = time;
                inProg = true;
            }
            if (!_racers.isEmpty()) {
                while (!_racers.isEmpty()) {
                    if (_racers.peek().getStartTime() != 0) return;
                    Racer r = _racers.removeFirst();
                    r.setStartTime(time);
                    _racerQueue.add(r);
                }

            }
        } else if(channelNum == 2){
            if(_racerQueue.isEmpty()){
                Racer r = new Racer(anonBib);
                r.setStartTime(sTime);
                r.setFinishTime(time);
                _finished.add(r);
                ++anonBib;
            } else {
                Racer r = _racerQueue.removeFirst();
                r.setFinishTime(time);
                _finished.add(r);
            }

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
        sTime = -1;
        inProg = false;
    }

    /**
     * Clears the racer with the given Bib Number from the race
     * @param bibNumber : Racers Bib Number to be cleared
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
     * Swaps the position of the first two Racers to finish
     */
    @Override
    public void swap() {
        // Not valid command for this RaceType
    }

    /**
     * Saves the run results
     */
    @Override
    public void saveRun(){
        Run r = new Run(this.toString());
        ArrayList<Racer> dn = new ArrayList<>();
        ArrayList<Racer> temp = new ArrayList<>();
        this.dnf();
        for(Racer ra : _finished){
            if(ra.getStartTime() == -1){
                dn.add(ra);
            } else {
                temp.add(ra);
            }
        }
        Comp c = new Comp();
        if(temp.size() > 0){
            for(int i = 0; i < temp.size()-1; ++i){
                int j = i+1;
                while(j < temp.size()){
                    if(c.compare(temp.get(i), temp.get(j)) >= 0){
                        Racer rTemp = temp.get(i);
                        temp.set(i, temp.get(j));
                        temp.set(j, rTemp);
                    }
                    ++j;
                }
            }
        }

        r.addResults(temp);
        r.addResults(_racers);
        r.addResults(dn);
        runs.add(r);
    }


    public class Comp implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            if(o1 instanceof Racer && o2 instanceof Racer) {
                Racer r1 = (Racer) o1;
                Racer r2 = (Racer) o2;
                long t = (r1.getFinishTime()-r1.getStartTime()) - (r2.getFinishTime() - r2.getStartTime());
                if(t == 0)
                    return 0;
                else if(t < 0)
                    return -1;
                else
                    return 1;
            }
            return -5;
        }
    }

    /**
     * Prints all the results from the race in a readable format
     * @return : String of results in readable format
     */
    @Override
    public String printResults() {
        String s = "";
        long t = System.currentTimeMillis();
        /*if(inProg){
            for(Racer r : _racerQueue){
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) +"\n";
            }
        } else {*/
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
       // }
        return s;
    }

    /**
     * Prints the result of a given run number
     * @param runNumber : Run number to print results for
     * @return : String of results in readable format
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
     * return the RaceType
     * @return : Racetype
     */
    @Override
    public String toString(){
        return "GRP";
    }

    /**
     * Gets the specified data from the race
     * @param type : String of where to grab the data from
     * @return : String of data in readable format
     */
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
            if(!q) {
                if (finished) {
                    String time = Time.getElapsed(r.getStartTime(), r.getFinishTime());
                    if(time.equals("-1"))
                        time = "DNF";
                    s += time;
                }
                else {
                    String time = Time.getElapsed(r.getStartTime(), cTime);
                    if(time.equals("-1"))
                        time = "DNF";
                    s += time;
                }
            }
            s += "\n";
        }
        return s;
    }
}
