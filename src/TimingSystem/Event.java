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
     * @param channels: Array of Channels
     */
    public Event(Channel[] channels){
        this("IND",channels);

    }

    /**
     * Constructor to create TimingSystem.Event
     * @param racetype: Type of event to be created
     * @param channels: Array of Channels
     */
    public Event(String racetype, Channel[] channels){
        Time time = new Time();
        switch(racetype.toUpperCase()){
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
            default:
                _racetype = new IND(channels);
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
     * Proxy method to call setStartTime
     * @param startTime
     * @param channelNum
     */
    @Override
    public void setStartTime(long startTime, int channelNum) {
        _racetype.setStartTime(startTime, channelNum);
    }

    /**
     * Proxy method to call setFinishTime
     * @param finishTime
     * @param channelNum
     */
    @Override
    public void setFinishTime(long finishTime, int channelNum) {
        _racetype.setFinishTime(finishTime, channelNum);
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
    public Run saveRun(){
        Run r =  _racetype.saveRun();
        r.setTimeStamp(timeStamp);
        runs.add(r);
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

    public void sendTime(String time){
        _time = time;
    }
}
