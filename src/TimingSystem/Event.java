/**
 * Event uses the Proxy Pattern in order to easily call every method needed
 */

package TimingSystem;

import TimingSystem.RaceTypes.*;
import Util.Time;

import java.util.ArrayList;

public class Event implements RaceType{
    private RaceType _racetype;
    private ArrayList<Run> runs;

    private String _time;
    private String timeStamp = "";
    /**
     * Default Constructor to handle default TimingSystem.Event type
     */
    public Event(){
        this("IND");

    }

    /**
     * Constructor to create TimingSystem.Event
     * @param racetype: Type of event to be created
     */
    public Event(String racetype){
        Time time = new Time();
        switch(racetype.toUpperCase()){
            case "IND":
                _racetype = new IND();
                break;
            case "PARIND":
                _racetype = new PARIND();
                break;
            case "GRP":
                _racetype = new GRP();
                break;
            case "PARGRP":
                _racetype = new PARGRP();
                break;
            default:
                _racetype = new IND();
                break;
        }
        runs = new ArrayList<>();
        _time = time.getSysTime();
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
     * Proxy method to call setTime
     * @param time
     * @param channelNum
     */
    @Override
    public void setTime(long time, int channelNum) {
        _racetype.setTime(time,channelNum);
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
     * Proxy method to call clear with a bib number
     * @param bibNumber
     */
    @Override
    public void clear(int bibNumber) {
        _racetype.clear(bibNumber);
    }

    /**
     * Proxy method to call swap
     */
    @Override
    public void swap() {
        _racetype.swap();
    }

    /**
     * Saves the results of the run
     * @return Results of the run
     */
    @Override
    public void saveRun(){
        runs.addAll(_racetype.getRuns());
        _racetype.saveRun();
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
     * Proxy method to call printResults with specified run number
     * @param runNumber : Run number
     * @return : Formatted run data
     */
    @Override
    public String printResults(int runNumber) {
        return _racetype.printResults(runNumber);
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
     * Proxy method to call getData
     * @param type : List to get data from
     * @return : Data from list as string
     */
    @Override
    public String getData(String type) {
        return _racetype.getData(type);
    }

    /**
     * Proxy method to call getLastRun
     * @return : Last run
     */
    @Override
    public Run getLastRun() {
        return _racetype.getLastRun();
    }

    /**
     * Proxy method to call getRuns
     * @return : List of runs
     */
    @Override
    public ArrayList<Run> getRuns() {
        return _racetype.getRuns();
    }

    /**
     * Proxy method to call dnf
     */
    @Override
    public void dnf() {
        _racetype.dnf();
    }

    /**
     * Returns the list of runs for the Event
     * @return list of runs for the current Event
     */
    public ArrayList<Run> sendRuns(){

        return _racetype.getRuns();
    }
}
