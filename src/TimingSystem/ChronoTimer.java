package TimingSystem;

import TimingSystem.Hardware.Channel;
import Util.*;
import java.util.ArrayList;

public class ChronoTimer {
    private State curState;
    private Util.Time sysTime;
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

        curState = State.OFF;
        channels = new Channel[8];
        for(int i = 0; i < 7; ++i) {
            channels[i] = new Channel(i+1);
        }
        sysTime = new Time();
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
                }
                break;
            case "TOG":
                if(runCalled){
                    //-1 for the index
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
                        event.setStartTime(System.currentTimeMillis());
                }
                else
                    event.setFinishTime(System.currentTimeMillis());

                break;
            //same as TRIG 1
            case "START":
                if(runCalled){
                    event.setStartTime(System.currentTimeMillis());
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    event.setFinishTime(System.currentTimeMillis());
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
                    //go to the ON state when the run is over so that there can be another run
                    eventList.add(event);
                    curState = State.ON;
                    runCalled =false;
                    eventCalled = false;
                }
                break;
            case "DNF":
                if(runCalled){
                    //assign the next up racer the DNF tag represented by -1 right now
                    event.setFinishTime(-1);
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
                if(curState.equals(State.ON)){
                    //when can TIME be called?
                    //do something with the system time
                }
                break;
            case "EXPORT":
                //checking whether event run exists to be exported
                if(!eventList.isEmpty() && curState == State.ON){
                    Event latest =  eventList.get(eventList.size()-1);
                    Simulation.export(this.getSysTime().toString(), latest.toString(), latest.sendRuns(), value);
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
                    if((channelNum % 2) != 0) {
                        event.setStartTime(Time.stringToMilliseconds(time));

                    }
                    else
                        event.setFinishTime(Time.stringToMilliseconds(time));
                }
                break;
            //same as TRIG 1
            case "START":
                if(runCalled){
                    event.setStartTime(Time.stringToMilliseconds(time));
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    event.setFinishTime(Time.stringToMilliseconds(time));
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
                    //go to the ON state when the run is over so that there can be another run
                    eventList.add(event);
                    curState = State.ON;
                    runCalled =false;
                    eventCalled = false;

                }
                break;
            case "DNF":
                if(runCalled){
                    //assign the next up racer the DNF tag represented by -1 right now
                    event.setFinishTime(-1);
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
                if(curState.equals(State.ON)){
                    //when can TIME be called?
                    //do something with the system time
                }
                break;
            case "EXPORT":
                //checking whether event run exists to be exported
                if(!eventList.isEmpty() && curState == State.ON){
                    Event latest =  eventList.get(eventList.size()-1);
                    Simulation.export(time, latest.toString(), latest.sendRuns(), value);
                }
                break;
        }
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
        sysTime = new Time();
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
