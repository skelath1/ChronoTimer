package TimingSystem;

import TimingSystem.Hardware.Channel;
import Util.*;

import java.util.*;

public class Event {
    private enum RACETYPE{
        IND, PARIND, GRP, PARGRP
    }

    private Channel[] _channels;

    // Will be used to return the racers
    //private Queue<TimingSystem.Racer> _racers;
    private Deque<Racer> _racers;


    // WIll be a linked list
    private Deque<Racer> _racerQueue;

    private RACETYPE _racetype;

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

//    /**
//     * Used to connect a sensor to a channel
//     * @param channelNum: TimingSystem.Hardware.Channel number to connect sensor to
//     * @param sensor: TimingSystem.Hardware.Sensor to connect to the channel
//     */
//    public void connectChannel(int channelNum, TimingSystem.Hardware.Sensor sensor){
//        _channels[channelNum-1].connectSensor(sensor);
//    }

    /**
     * Creates new TimingSystem.Racer and adds it to the TimingSystem.Racer Queue
     * @param bibNumber: bib number of racer
     */
    public void addRacer(int bibNumber){
        _racers.add(new Racer(bibNumber));
    }

    /**
     *  Get/Removes first TimingSystem.Racer from _racers, then sets start time and
     *  Adds the racer to _racerQueue
     * @param startTime
     */
    public void setStartTime(long startTime){
        if(!_racers.isEmpty()){
            Racer r = _racers.remove();
            r.setStartTime(startTime);
            _racerQueue.add(r);
        }
    }

    /**
     * Get/Removes first TimingSystem.Racer from _racerQueue, then sets finish time and
     * Adds the racer to _racers
     * @param finishTime
     */
    public void setFinishTime(long finishTime){
        if(!_racerQueue.isEmpty()){
            if(_racerQueue.peek().getStartTime() != -1) {
                Racer r = _racerQueue.remove();
                r.setFinishTime(finishTime);
                _racers.add(r);
            }
        }
    }
    public void cancelRacer(){
        Racer r = _racerQueue.removeLast();
        r.setStartTime(-1);
        r.setFinishTime(-1);
        _racers.addFirst(r);
    }

    public String printResults(){
        String s = "";
        for(Racer r : _racers) {
            if(r.getFinishTime() == -1)
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + "DNF\n";
            else
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) + "\n";
        }
        return s;
    }

    public void clear(){
        _racerQueue = new LinkedList<>();
        _racers = new LinkedList<>();
    }
}
