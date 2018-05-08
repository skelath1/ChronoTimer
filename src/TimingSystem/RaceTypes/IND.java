package TimingSystem.RaceTypes;

import TimingSystem.Racer;
import TimingSystem.Result;
import TimingSystem.Run;
import Util.Time;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

public class IND implements RaceType {
    private Deque<Racer> _racers;
    private Deque<Racer> _finished;
    private Deque<Racer> _racerQueue;

    private ArrayList<Run> runs;

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
     * @param bibNumber : bib number of racer
     */
    @Override
    public void addRacer(int bibNumber) {
        if(validNewRacer(bibNumber))
            _racers.add(new Racer(bibNumber));
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
        if(_racerQueue.isEmpty()) return;
        Racer r = _racerQueue.removeFirst();
        r.setStartTime(-1);
        r.setFinishTime(-1);
        _finished.add(r);

    }

    /**
     * Checks whether the racer is already in the event
     * @param bibNumber : Racer to check
     * @return : If racer is in the event
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
                if(_racerQueue.peek().getStartTime() != 0) {
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
        if(!_racerQueue.isEmpty()) {
            Racer r = _racerQueue.removeLast();
            r.setStartTime(-1);
            r.setFinishTime(-1);
            _racers.addFirst(r);
        }
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
     * Swaps next two racers to finish
     */
    @Override
    public void swap() {
        if(_racerQueue.size() >= 2) {
            Racer r1 = _racerQueue.removeFirst();
            Racer r2 = _racerQueue.removeFirst();
            _racerQueue.addFirst(r1);
            _racerQueue.addFirst(r2);
        }
    }

    /**
     * Saves the run in runs to be used in export
     */
    @Override
    public void saveRun(){
        Run r = new Run(this.toString());
        ArrayList<Racer> temp = new ArrayList<>();
        ArrayList<Racer> dn = new ArrayList<>();
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

    public class Comp implements Comparator{

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
     * Puts the results of the race into a readable format
     * @return : String of the results of the race
     */
    @Override
    public String printResults() {
        String s = "";

        //changed from racer to racerQueue
        long t = System.currentTimeMillis();
//        if(!runs.isEmpty() && null != runs.get(runs.size()-1)) {
//
//            if (inProg) {
//                for (Racer r : _racerQueue) {
//                    s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) + "\n";
//                }
//            } else {
                if (runs.isEmpty()) {
                    for (Racer r : _finished) {
                        String time = Time.getElapsed(r.getStartTime(), r.getFinishTime());
                        if(time.trim().equals("-1"))
                            time = "DNF";
                        s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + time + "\n";
                    }
                } else {
                    Run r = runs.get(runs.size() - 1);
                    for (Result res : r.getResults()) {
                        String time = res.get_time();
                        if(time.trim().equals("-1"))
                            time = "DNF";
                        s += "TimingSystem.Racer: " + res.get_bib() + " : " + res.get_time() + "\n";
                    }
                }
          //  }
        //}
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
        Run r = runs.get(runNumber - 1);
        for (Result res : r.getResults()) {
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
     * Grabs data for specified list
     * @param type : Place to get data from
     * @return : Readable version of data
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
        }
        return data;
    }

    /**
     * Converts a list to a string
     * @param e : List to convert
     * @param running : If the racers are in progress
     * @param finished : If the racers are finished
     * @return : Converted List in string format
     */
    private String listToString(Deque<Racer> e, boolean running,  boolean finished){
        String data = "";
        long cTime = System.currentTimeMillis();
        for(Racer r : e){
            data += r.getBibNumber() + ": ";
            if(running) {
                String time = Time.getElapsed(r.getStartTime(), cTime);
                if(time.equals("-1"))
                    time = "DNF";
                data += time;
            }
            if(finished) {
                String time = Time.getElapsed(r.getStartTime(), r.getFinishTime());
                if(time.equals("-1"))
                    time = "DNF";
                data += time;
            }
            data += " \n";
        }

        return data;
    }
}
