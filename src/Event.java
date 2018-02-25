import Util.*;

import java.util.LinkedList;
import java.util.Queue;

public class Event {
    private enum RACETYPE{
        IND, PARIND, GRP, PARGRP
    }

    private Channel[] _channels;

    // Will be used to return the racers
    private Queue<Racer> _racers;


    // WIll be a linked list
    private Queue<Racer> _racerQueue;

    private RACETYPE _racetype;

    /**
     * Default Constructor to handle default Event type
     */
    public Event(Channel[] channels){
        this("IND",channels);
    }

    /**
     * Constructor to create Event
     * @param racetype: Type of event to be created
     */
    public Event(String racetype, Channel[] channels){
        switch(racetype){
            case "IND":
                _racetype = RACETYPE.IND;
                break;
            case "PARIND":
                _racetype = RACETYPE.PARIND;
                break;
            case "GRP":
                _racetype = RACETYPE.GRP;
                break;
            case "PARGRP":
                _racetype = RACETYPE.PARGRP;
                break;
        }
        _racers = new LinkedList<>();
        _channels = channels;
        _racerQueue = new LinkedList<>();
    }

    /**
     * Used to toggle a channel on or off
     * @param channelNum: Channel number to toggle
     */
    public void toggleChannel(int channelNum){
        _channels[channelNum-1].toggle();
    }

    /**
     * Used to connect a sensor to a channel
     * @param channelNum: Channel number to connect sensor to
     * @param sensor: Sensor to connect to the channel
     */
    public void connectChannel(int channelNum, Sensor sensor){
        _channels[channelNum-1].connectSensor(sensor);
    }

    /**
     * Creates new Racer and adds it to the Racer Queue
     * @param bibNumber: bib number of racer
     */
    public void addRacer(int bibNumber){
        _racers.add(new Racer(bibNumber));
    }

    /**
     *  Get/Removes first Racer from _racers, then sets start time and
     *  Adds the racer to _racerQueue
     * @param startTime
     */
    public void setStartTime(long startTime){
        Racer r = _racers.remove();
        r.setStartTime(startTime);
        _racerQueue.add(r);
    }

    /**
     * Get/Removes first Racer from _racerQueue, then sets finish time and
     * Adds the racer to _racers
     * @param finishTime
     */
    public void setFinishTime(long finishTime){
        Racer r = _racerQueue.remove();
        r.setFinishTime(finishTime);
        _racers.add(r);
    }

    public Racer getRacer(){
        return _racers.peek();
    }

    public Racer getQRacer(){
        return _racerQueue.peek();
    }
}
