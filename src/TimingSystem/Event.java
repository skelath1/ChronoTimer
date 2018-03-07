/**
 * Event uses the Proxy Pattern in order to easily call every method needed
 */

package TimingSystem;

import TimingSystem.Hardware.Channel;
import TimingSystem.RaceTypes.*;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Event implements RaceType{
    private RaceType _racetype;
    private ArrayList<Run> runs;

    /**
     * Default Constructor to handle default TimingSystem.Event type
     */
    public Event(Channel[] channels){
        this("IND",channels);
    }

    /**
     * Constructor to create TimingSystem.Event
     * @param racetype: Type of event to be created
     */
    public Event(String racetype, Channel[] channels){
        switch(racetype){
            case "IND":
                _racetype = new IND(channels);
                break;
            case "PARIND":
                _racetype = new PARIND(channels);
                break;
            case "GRP":
                _racetype = new GRP(channels);
                break;
            case "PARGRP":
                _racetype = new PARGRP(channels);
                break;
        }
        runs = new ArrayList<>();
    }

    /**
     * Proxy method to call addRacer
     * @param bibNumber
     */

    @Override
    public void addRacer(int bibNumber) {
        _racetype.addRacer(bibNumber);
    }

    /**
     * Proxy method to call setStartTime
     * @param startTime
     */
    @Override
    public void setStartTime(long startTime) {
        _racetype.setStartTime(startTime);
    }

    /**
     * Proxy method to call setFinishTime
     * @param finishTime
     */
    @Override
    public void setFinishTime(long finishTime) {
        _racetype.setFinishTime(finishTime);
    }

    /**
     * Proxy method to call cancelRacer
     */
    @Override
    public void cancelRacer() {
        _racetype.cancelRacer();
    }

    /**
     * Proxy method to call clear
     */
    @Override
    public void clear() {
        _racetype.clear();
    }

    /**
     * Saves the results of the run
     * @return Results of the run
     */

    @Override
    public Run saveRun(){
        Run r =  _racetype.saveRun();
        runs.add(r);
        Gson g = new Gson();
        System.out.println(g.toJson(runs));

        return r;
    }

    /**
     * Proxy method to call printResults
     * @return
     */
    @Override
    public String printResults() {
        return _racetype.printResults();
    }

    /**
     * Proxy method to call toString
     * @return type of the Event
     */
    @Override
    public String toString(){
        return _racetype.toString();
    }

    /**
     * Returns the list of runs for the Event
     * @return list of runs for the current Event
     */
    public ArrayList<Run> sendRuns(){
        return runs;
    }
}
