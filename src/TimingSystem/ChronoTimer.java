package TimingSystem;

import TimingSystem.Hardware.Channel;
import Util.*;

import java.text.NumberFormat;
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
                saveRun();
                break;
            case "POWER":
                power();
                break;
            case "EVENT":
                event(value);
                break;
            case "NEWRUN":
                newRun();
                break;
            case "TOG":
                //error check value for valid string to num
                tog(value);
                break;
            case "NUM":
                //error check value for valid string to num
                num(value);
                break;
            case "TRIG":
                if(runCalled){
                    int channelNum = Integer.parseInt(value);
                    //if it is odd then it is the start
                    if((channelNum % 2) != 0)
                        event.setStartTime(System.currentTimeMillis(), channelNum);
                    else
                        event.setFinishTime(System.currentTimeMillis(), Integer.parseInt(value));
                }
                break;
            //same as TRIG 1
            case "START":
                if(runCalled){
                    event.setStartTime(System.currentTimeMillis(), 1);
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    event.setFinishTime(System.currentTimeMillis(), 2);
                }
                break;
            case "PRINT":
                print(value);
                break;
            case "ENDRUN":
               endRun();
                break;
            case "DNF":
               dnf();
                break;
            case "CANCEL":
                cancel();
                break;
            case "EXIT":
               exit();
                break;
            case "TIME":
                if(runCalled){
                    sysTime.setSysTime(value);
                }
                break;
            case "EXPORT":
                export(value);
                break;
            case "RESET":
                reset();
                break;
            case "SWAP":
                swap();
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
                saveRun();
                break;
            case "POWER":
                power();
                break;
            case "EVENT":
                event(value);
                break;
            case "NEWRUN":
                newRun();
                break;
            case "TOG":
                //error check value for valid string to num
                tog(value);
                break;
            case "NUM":
                //error check value for valid string to num
                num(value);
                break;
            case "TRIG":
                if(runCalled){
                    int channelNum = Integer.parseInt(value);
                    //if it is odd then it is the start
                    if((channelNum % 2) != 0)
                        event.setStartTime(Time.stringToMilliseconds(time), channelNum);
                    else
                        event.setFinishTime(Time.stringToMilliseconds(time), Integer.parseInt(value));
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
                print(value);
                break;
            case "ENDRUN":
                endRun();
                break;
            case "DNF":
                dnf();
                break;
            case "CANCEL":
                cancel();
                break;
            case "EXIT":
                exit();
                break;
            case "TIME":
                if(runCalled){
                    sysTime.setSysTime(value);
                }
                break;
            case "EXPORT":
                export(value);
                break;
            case "RESET":
                reset();
                break;
            case "SWAP":
                swap();
                break;
        }
    }

    //util methods
    /**
     *
     * @return sysTime
     */
    public Time getSysTime()
    {
        return sysTime;
    }
    public String getState(){return curState.toString();}


    //case methods
    private void saveRun(){
        event.saveRun();
    }
    private void power(){
        if(curState.equals(State.OFF)){
            curState = State.ON;
        }
        else{
            runCalled = false;
            eventCalled = false;
            eventList = new ArrayList<>();
            event = null;
            curState = State.OFF;
            for(Channel c: channels){
                if(c != null)
                    c.toggle();
            }
        }
    }
    private void newRun(){
        if((curState.equals(State.EVENT) || curState.equals(State.ON)) && !runCalled){
            runCalled =true;
        }

    }
    private void event(String value){
        if(curState.equals(State.ON) && !eventCalled) {
            if(value == null)
                event = new Event(channels);
            else {
                event = new Event(value, channels);
            }
            curState = State.EVENT;
            eventCalled = true;
        }
    }
    private void tog(String value){
        if(runCalled){
            // too late to call event
            eventCalled = true;
            if(event == null) {
                //creating a new event if there wasn't one
                event = new Event(channels);
            }
            try{
                int channelIndex = Integer.parseInt(value) -1;
                channels[channelIndex].toggle();
            }
            catch (NumberFormatException nfe){
                Simulation.execute("ERROR","Invalid argument");
            }
        }
    }
    private void reset(){
        runCalled = false;
        eventCalled = false;
        eventList = new ArrayList<>();
        event = null;
        curState = State.ON;
        for(Channel c: channels){
            c.toggle();
        }

    }
    private void num(String value){
        if(runCalled){
            eventCalled = true; // too late to call event
            try{
                event.addRacer(Integer.parseInt(value));
            }catch(NumberFormatException nfm){
                Simulation.execute("ERROR","Invalid argument");
            }
        }
    }
    private void cancel(){
        if(runCalled){
            //put the racer back in the queue at the beginning
            event.cancelRacer();
        }
    }
    private void print(String value){
        //needs to be able to take in parameter
        if(runCalled){
            if(value != null){
                try{
                    if(value != null){
                        event.printResults(Integer.parseInt(value));
                    }
                    else
                        event.printResults();

                }catch(NumberFormatException nfm){
                    Simulation.execute("ERROR","Invalid argument");
                }
            }
            else{
                //print the most recent run
                Simulation.execute("PRINT",event.printResults());
            }
        }
    }
    private void export(String value){
        //checking whether event run exists to be exported
        System.out.println(eventList.isEmpty());
        if(!eventList.isEmpty() && (curState == State.EVENT || curState == State.ON)){
            //get all the runs if value is null
            if(value == null){
                    Simulation.export(event.sendRuns());
            }
            //else get run from value given
            else{
                try {
                    Event latest = eventList.get(Integer.parseInt(value) - 1);
                    Simulation.export(latest.sendRuns(), value);
                }
                catch(NumberFormatException nfe){
                    Simulation.execute("ERROR","Invalid argument");
                }
            }
        }
    }
    private void exit(){
        if(runCalled){
            Simulation.execute("EXIT",null);
        }
    }
    private void endRun(){
        if(runCalled && event!= null){
            eventList.add(event);
            event.saveRun();
            event.clear();
            curState = State.ON;
            runCalled = false;
            eventCalled = false;
        }
    }
    private void dnf(){
        if(runCalled){
            //assign the next up racer the DNF tag represented by -1 right now
            event.setFinishTime(-1, 0);
        }
    }
   private void swap(){
        if(runCalled) {
            event.swap();
        }
   }
}
