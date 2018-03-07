package TimingSystem;

import TimingSystem.Hardware.Channel;
import TimingSystem.RaceTypes.*;

import java.util.ArrayList;
import java.util.Collection;

public class Event implements RaceType{
    private RaceType _racetype;
    private Collection<Run> runs;

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


    @Override
    public void addRacer(int bibNumber) {
        _racetype.addRacer(bibNumber);
    }

    @Override
    public void setStartTime(long startTime) {
        _racetype.setStartTime(startTime);
    }

    @Override
    public void setFinishTime(long finishTime) {
        _racetype.setFinishTime(finishTime);
    }

    @Override
    public void cancelRacer() {
        _racetype.cancelRacer();
    }

    @Override
    public void clear() {
        _racetype.clear();
    }

    @Override
    public String printResults() {
        return _racetype.printResults();
    }
}
