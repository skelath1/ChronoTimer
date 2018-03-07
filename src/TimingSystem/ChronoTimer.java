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


    public enum State{
        ON,OFF,EVENT,RUN,TOG,INPUTRACERS,INPROGRESS
    }


    public ChronoTimer(){
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
        Simulation.execute("PRINT", " COMMAND: "+ command + " VALUE: " + value + " STATE: " + curState.toString());
        switch(command.toUpperCase())
        {
            case "SAVE":
                event.saveRun();
                break;
            case "POWER":
                if(curState.equals(State.OFF)){
                    curState = State.ON;
                    //setting the interal clock
                }
                else
                    curState = State.OFF;
                break;
            case "EVENT":
                if(curState.equals(State.ON)) {
                    if(value == null)
                        event = new Event(channels);
                    else
                        event = new Event(value, channels);
                     curState = State.EVENT;
                }
                break;
            case "NEWRUN":
                if(curState.equals(State.EVENT)){
                    //curState = State.RUN;
                }
                break;
            case "TOG":
                if(curState.equals(State.EVENT)){
                    //-1 for the index
                    int channelIndex = Integer.parseInt(value) - 1;
                    channels[channelIndex].toggle();

                    curState = State.TOG;
                }
                else if(curState.equals(State.TOG)){
                    int channelIndex = Integer.parseInt(value) - 1;
                    channels[channelIndex].toggle();
                    curState = State.INPUTRACERS;
                }
                break;
            case "NUM":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.EVENT)){
                    event.addRacer(Integer.parseInt(value));
                    curState = State.INPUTRACERS; // in the case there is another event
                    //dont change the state because may need to enter multiple racers.
                }
                break;
            case "TRIG":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS)){
                    int channelNum = Integer.parseInt(value);

                    //if it is odd then it is the start
                    if((channelNum % 2) != 0) {
                        event.setStartTime(System.currentTimeMillis());
                        curState = State.INPROGRESS;
                    }
                    //only finish if the there was already a start
                    else if(curState.equals(State.INPROGRESS))
                        event.setFinishTime(System.currentTimeMillis());
                }
                break;
            //same as TRIG 1
            case "START":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS) || curState.equals(State.EVENT)){
                    event.setStartTime(System.currentTimeMillis());
                    curState = State.INPROGRESS;
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(curState.equals(State.INPUTRACERS)){
                    event.setFinishTime(System.currentTimeMillis());
                }
                break;
            case "PRINT":
                if(curState.equals(State.INPROGRESS)){
                    //print the results of the race
                    //send to simulation to print
                    Simulation.execute("PRINT",event.printResults());
                }
                break;
            case "ENDRUN":
                if(curState.equals(State.INPROGRESS)){
                    //go to the ON state when the run is over so that there can be another run
                    eventList.add(event);
                    curState = State.ON;
                    event.clear();
                    //System.out.println(event.printResults());
                }
                break;
            case "DNF":
                if(curState.equals(State.INPROGRESS)){
                    //assign the next up racer the DNF tag represented by -1 right now
                    event.setFinishTime(-1);
                }
                break;
            case "CANCEL":
                if(curState.equals(State.INPROGRESS)){
                   //put the racer back in the queue at the beginning
                    event.cancelRacer();
                }
                break;
            case "EXIT":
                if(curState.equals(State.INPROGRESS)){
                    //just reset the fields so its like restarting at
                   Simulation.execute("EXIT",null);
                }
                break;
            case "TIME":
                if(curState.equals(State.INPROGRESS)){
                    //when can TIME be called?
                    //do something with the system time
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
       Simulation.execute("PRINT",time + " COMMAND: "+ command + " VALUE: " + value + " STATE: " + curState.toString());
        switch(command.toUpperCase())
        {
            case "SAVE":
                event.saveRun();
                break;
            case "POWER":
                if(curState.equals(State.OFF)){
                    curState = State.ON;
                    //setting the interal clock
                }
                else
                    curState = State.OFF;
                break;
            case "EVENT":
                if(curState.equals(State.ON)) {
                    if(value == null)
                        event = new Event(channels);
                    else
                        event = new Event(value, channels);
                    curState = State.EVENT;
                }
                break;
            case "NEWRUN":
                if(curState.equals(State.EVENT)){
                    //curState = State.RUN;
                }
                break;
            case "TOG":
                if(curState.equals(State.EVENT)){
                    //-1 for the index
                    int channelIndex = Integer.parseInt(value) - 1;
                    channels[channelIndex].toggle();

                    curState = State.TOG;
                }
                else if(curState.equals(State.TOG)){
                    int channelIndex = Integer.parseInt(value) - 1;
                    channels[channelIndex].toggle();
                    curState = State.INPUTRACERS;
                }
                break;
            case "NUM":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.EVENT)){
                    event.addRacer(Integer.parseInt(value));
                    //dont change the state because may need to enter multiple racers.
                }
                break;
            case "TRIG":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS) || curState.equals(State.EVENT)){
                    int channelNum = Integer.parseInt(value);

                    if(channels[channelNum-1].isOn()){
                        //if it is odd then it is the start
                        if((channelNum % 2) != 0) {
                            event.setStartTime(Time.stringToMilliseconds(time));
                            curState = State.INPROGRESS;
                        }
                        //only finish if the there was already a start
                        else if(curState.equals(State.INPROGRESS))
                            event.setFinishTime(Time.stringToMilliseconds(time));
                    }
                }
                break;
            //same as TRIG 1
            case "START":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS)){
                    event.setStartTime(Time.stringToMilliseconds(time));
                    curState = State.INPROGRESS;
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(curState.equals(State.INPUTRACERS)){
                    event.setFinishTime(Time.stringToMilliseconds(time));
                }
                break;
            case "PRINT":
                if(curState.equals(State.INPROGRESS) || curState.equals(State.EVENT)){
                    //print the results of the race
                    //send to simulation to print
                    Simulation.execute("PRINT",event.printResults());
                }
                break;
            case "ENDRUN":
                if(curState.equals(State.INPROGRESS)){
                    //go to the ON state when the run is over so that there can be another run
                    eventList.add(event);
                    curState = State.ON;
                    event.clear();
                }
                break;
            case "DNF":
                if(curState.equals(State.INPROGRESS)){
                    //assign the next up racer the DNF tag represented by -1 right now
                    event.setFinishTime(-1);
                }
                break;
            case "CANCEL":
                if(curState.equals(State.INPROGRESS)){
                    //put the racer back in the queue at the beginning
                    event.cancelRacer();
                }
                break;
            case "EXIT":
                if(curState.equals(State.INPROGRESS)){
                    //just reset the fields so its like restarting at
                    Simulation.execute("PRINT","EXITING SIMULATOR...");
                }
                break;
            case "TIME":
                if(curState.equals(State.INPROGRESS)){
                    //when can TIME be called?
                    //do something with the system time
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
