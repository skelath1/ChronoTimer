package TimingSystem;

import TimingSystem.Hardware.Channel;
import Util.*;
import java.util.ArrayList;

public class ChronoTimer {
    private State curState;
    private Time sysTime;
    private Event event;
    private Channel channels[];
    private ArrayList<Event> eventList;     //used to store all the previous events
    private boolean timeSet = false;
    private boolean eventCalled;
    private boolean runCalled;


    public enum State{
        ON,OFF,EVENT
    }


    public ChronoTimer(){
        boolean eventCalled = false;
        boolean runCalled = false;
        sysTime = new Time();
        curState = State.OFF;
        channels = new Channel[8];
        for(int i = 0; i < 7; ++i) {
            channels[i] = new Channel(i+1);
        }

        eventList = new ArrayList<>();

    }

    /**
     * @param command String
     * @param value String
     * Takes in command from TimingSystem.Simulation and executes it.
     */
    public void execute(String command, String value){
        Simulation.execute("PRINT", " COMMAND: "+ command + " VALUE: " + value + " STATE: " + curState.toString()+ " runCalled " + runCalled + " eventCalled " + eventCalled);
        switch(command.toUpperCase())
        {
            case "SAVE":
                event.saveRun();
                break;
            case "POWER":
                if(curState.equals(State.OFF)){
                    curState = State.ON;
                }
                else
                    curState = State.OFF;
                break;
            case "EVENT":
                if(curState.equals(State.ON) && !eventCalled) {
                    if(value == null)
                        event = new Event(channels);
                    else
                        event = new Event(value, channels);

                    curState = State.EVENT;
                    eventCalled = true;
                }
                break;
            case "NEWRUN":
                if((curState.equals(State.EVENT) || curState.equals(State.ON)) && !runCalled){
                    runCalled =true;
                    event.newRun(sysTime.getSysTime());
                }
                break;
            case "TOG":
                if(runCalled){
                    //-1 for the index
                    eventCalled = true; // too late to call event
                    if(event == null) {
                        //creating a new event
                        event = new Event(channels);
                    }
                    int channelIndex = Integer.parseInt(value) - 1;
                    channels[channelIndex].toggle();
                }
                break;
            case "NUM":
                if(runCalled){
                    eventCalled = true; // too late to call event
                    event.addRacer(Integer.parseInt(value));
                }
                break;
            case "TRIG":
                if(runCalled){
                    int channelNum = Integer.parseInt(value);
                    //if it is odd then it is the start
                    if((channelNum % 2) != 0)
                        event.setStartTime(Time.stringToMilliseconds(sysTime.getSysTime()), channelNum);
                    else
                        event.setFinishTime(Time.stringToMilliseconds(sysTime.getSysTime()), Integer.parseInt(value));
                }
                break;
            //same as TRIG 1
            case "START":
                if(runCalled){
                    event.setStartTime(Time.stringToMilliseconds(sysTime.getSysTime()), 1);
                }
                break;

            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    event.setFinishTime(Time.stringToMilliseconds(sysTime.getSysTime()), 2);
                }
                break;
            case "PRINT":
                if(runCalled){
                    //send to simulation to print
                    Simulation.execute("PRINT",event.printResults());
                }
                break;
            case "ENDRUN":
                if(runCalled && event!= null){
                    eventList.add(event);
                    event.saveRun();
                    curState = State.ON;
                    runCalled =false;
                    eventCalled = false;
                }
                break;
            case "DNF":
                if(runCalled){
                    //assign the next up racer the DNF tag represented by -1 right now
                    event.setFinishTime(-1, 0);
                }
                break;
            case "CANCEL":
                if(runCalled){
                    //put the racer back in the queue at the beginning
                    event.cancelRacer();
                }
                break;
            case "EXIT":
                if(runCalled){
                    Simulation.execute("EXIT",null);
                }
                break;
            case "TIME":
                if(runCalled){
                    sysTime.setSysTime(value);
                }
                break;
            case "EXPORT":
                //checking whether event run exists to be exported
                if(!eventList.isEmpty() && curState == State.ON){
                    Event latest;
                    if(value == null){//get latest run
                        int runNumber = eventList.size();
                        latest =  eventList.get(eventList.size()-1);
                        Simulation.export(latest.sendRuns(), Integer.toString(runNumber));
                    }else{//else get run from value given
                        latest = eventList.get(Integer.parseInt(value)-1);
                        Simulation.export(latest.sendRuns(), value);
                    }
                }
                break;
        }
    }

    /**
     * @param time String
     * @param command String
     * @param value String
     * Takes in command from TimingSystem.Simulation and executes it.
     */
    public void execute(String time, String command, String value){
        Simulation.execute("PRINT",time + " COMMAND: "+ command + " VALUE: " + value + " STATE: " + curState.toString() + " runCalled " + runCalled + " eventCalled " + eventCalled);
        switch(command.toUpperCase())
        {
            case "SAVE":
                event.saveRun();
                break;
            case "POWER":
                if(curState.equals(State.OFF)){
                    curState = State.ON;
                }
                else
                    curState = State.OFF;
                break;
            case "EVENT":
                if(curState.equals(State.ON) && !eventCalled) {
                    if(value == null)
                        event = new Event(channels);
                    else
                        event = new Event(value, channels);
                    curState = State.EVENT;
                    eventCalled = true;
                }
                break;
            case "NEWRUN":
                if((curState.equals(State.EVENT) || curState.equals(State.ON)) && !runCalled){
                    runCalled =true;
                    event.newRun(time);
                }
                break;
            case "TOG":
                if(runCalled){
                    eventCalled = true; // too late to call event
                    int channelIndex = Integer.parseInt(value) - 1;
                    channels[channelIndex].toggle();
                }
                break;
            case "NUM":
                if(runCalled){
                    eventCalled = true; // too late to call event
                    event.addRacer(Integer.parseInt(value));
                }
                break;
            case "TRIG":
                if(runCalled){
                    int channelNum = Integer.parseInt(value);

                    //if it is odd then it is the start
                    if((channelNum % 2) != 0)
                        event.setStartTime(Time.stringToMilliseconds(time), channelNum);
                    else
                        event.setFinishTime(Time.stringToMilliseconds(time), channelNum);
                }
                break;
            //same as TRIG 1
            case "START":
                if(runCalled){
                    event.setStartTime(Time.stringToMilliseconds(time), 1);
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    event.setFinishTime(Time.stringToMilliseconds(time),2);
                }
                break;
            case "PRINT":
                if(runCalled){
                    //send to simulation to print
                    Simulation.execute("PRINT",event.printResults());
                }
                break;
            case "ENDRUN":
                if(runCalled){
                    eventList.add(event);
                    event.saveRun();
                    curState = State.ON;
                    runCalled =false;
                    eventCalled = false;
                }
                break;
            case "DNF":
                if(runCalled){
                    //assign the next up racer the DNF tag represented by -1 right now
                    event.setFinishTime(-1,0);
                }
                break;
            case "CANCEL":
                if(runCalled){
                    //put the racer back in the queue at the beginning
                    event.cancelRacer();
                }
                break;
            case "EXIT":
                if(runCalled){
                    Simulation.execute("EXIT",null);
                }
                break;
            case "TIME":
                if(runCalled){
                    sysTime.setSysTime(time);
                }
                break;
            case "EXPORT":
                //checking whether event run exists to be exported
                this.execute(command, value);
                break;
        }
        sysTime.setSysTime(time);

    }

    /**
     * resets all the fields for RESET
     */
    private void clearFields(){
        curState = State.OFF;
        channels = new Channel[8];
        for(int i = 0; i < 7; ++i) {
            channels[i] = new Channel(i+1);
        }
        eventList = new ArrayList<>();
        event = null;
    }

    /**
     *
     * @return sysTime
     */
    public Time getSysTime()
    {
        return sysTime;
    }
    private String getState(){return curState.toString();}
}
