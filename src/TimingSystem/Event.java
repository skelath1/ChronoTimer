/**
 * Event uses the Proxy Pattern in order to easily call every method needed
 */

package TimingSystem;

import TimingSystem.Hardware.Channel;
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

//    /**
//     * Proxy method to call setStartTime
//     * @param startTime
//     * @param channelNum
//     */
//    @Override
//    public void setStartTime(long startTime, int channelNum) {
//            _racetype.setStartTime(startTime, channelNum);
//    }
//
//    /**
//     * Proxy method to call setFinishTime
//     * @param finishTime
//     * @param channelNum
//     */
//    @Override
//    public void setFinishTime(long finishTime, int channelNum) {
//        _racetype.setFinishTime(finishTime, channelNum);
//    }

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

    @Override
    public void clear(int bibNumber) {
        _racetype.clear(bibNumber);
    }

    @Override
    public void swap() {
        _racetype.swap();
    }

    public void newRun(String timeStamp) {
        this.timeStamp = timeStamp;
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


    @Override
    public String printResults(int runNumber) {
        return _racetype.printResults(runNumber);
    }
    //TODO does this need to implemented?

    /**
     * Proxy method to call toString
     * @return type of the Event
     */
    @Override
    public String toString(){
        return _racetype.toString();
    }

    @Override
    public String getData(String type) {
        return _racetype.getData(type);
    }

    @Override
    public Run getLastRun() {
        return _racetype.getLastRun();
    }

    @Override
    public ArrayList<Run> getRuns() {
        return _racetype.getRuns();
    }

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

    public void sendTime(String time){
        _time = time;
    }
}
