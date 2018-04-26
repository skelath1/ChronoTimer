package TimingSystem;

import TimingSystem.Hardware.Channel;
import TimingSystem.Hardware.Sensor;
import TimingSystem.Hardware.SensorFactory;
import Util.*;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ChronoTimer {
    private State curState;
    private Time sysTime;
    private Event event;
    private Channel channels[];
    private ArrayList<Event> eventList;
    private boolean timeSet = false;
    private boolean eventCalled;
    private boolean runCalled;
    private ChronoClient client;


    public enum State{
        ON,OFF,EVENT
    }


    public ChronoTimer(){
        boolean eventCalled = false;
        boolean runCalled = false;
        sysTime = new Time();
        curState = State.OFF;
        channels = new Channel[8];
        for(int i = 0; i < 8; ++i) {
            channels[i] = new Channel(i+1);
        }
        eventList = new ArrayList<>();
        //connecting to the server
        client = new ChronoClient();
    }

    /**
     * @param command String
     * @param value String
     * Takes in command from TimingSystem.Simulation and executes it.
     */
    public void execute(String command, String value,String value2){
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
                    try {
                        int channelNum = Integer.parseInt(value);
                        //if it is odd then it is the start

                        if(channels[channelNum-1].isReady()) {
                            event.setTime(channels[channelNum - 1].triggerSensor(), channelNum);
                        }

                    }
                    catch(NumberFormatException ex){
                        Simulation.execute("ERROR"," " + value + " not valid.");
                    }
                }
                break;
            //same as TRIG 1
            case "START":
                if(runCalled){
                    if(channels[0].isReady())
                        event.setTime(channels[0].triggerSensor(), 1);
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    if(channels[1].isReady())
                        event.setTime(channels[1].triggerSensor(), 2);
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
            case "CLR":
                clear(value);
                break;
            case "CONN":
                connect(value,value2);
                break;
        }
    }
    /**
     * @param time String
     * @param command String
     * @param value String
     * Takes in command from TimingSystem.Simulation and executes it.
     */
    public void execute(String time, String command, String value, String value2){
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
                if(runCalled) {
                    if (runCalled) {
                        try {
                            int channelNum = Integer.parseInt(value);
                            //if it is odd then it is the start
                            if(channels[channelNum-1].isReady()) {
                                event.setTime(channels[channelNum - 1].triggerSensor(), channelNum);
                            }
                        } catch (NumberFormatException ex) {
                            Simulation.execute("ERROR", " " + value + " not valid.");
                        }
                    }
                }
                break;
            //same as TRIG 1
            case "START":
                if(runCalled){
                    if(channels[0].isReady())
                        event.setTime(channels[0].triggerSensor(), 1);
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    if(channels[1].isReady())
                        event.setTime(channels[1].triggerSensor(), 2);
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
            case "CLR":
                clear(value);
                break;
            case "CONN":
                connect(value,value2);
                break;

        }
    }

    //util methods



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
                    c.setOff();
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
                event = new Event();
            else {
                event = new Event(value);
            }
            curState = State.EVENT;
            eventCalled = true;
        }
    }
    private void tog(String value){
        if(runCalled || eventCalled){
            // too late to call event
            eventCalled = true;
            runCalled = true;
            if(event == null) {
                //creating a new event if there wasn't one
                event = new Event();
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
        if(runCalled || eventCalled){
            eventCalled = true; // too late to call event
            runCalled = true;
            if(event == null) {
                //creating a new event if there wasn't one
                event = new Event();
            }
            try{
                event.addRacer(Integer.parseInt(value));
            }catch(NumberFormatException nfm){
                Simulation.execute("ERROR","Invalid argument");
            }
        }
    }
    private void cancel(){
        if(runCalled && eventCalled){
            //put the racer back in the queue at the beginning
            event.cancelRacer();
        }
    }
    private void print(String value){

        if(runCalled) {
            if (value != null) {
                try {

                    Simulation.execute("PRINT", event.printResults(Integer.parseInt(value)));

                } catch (NumberFormatException nfm) {
                    Simulation.execute("ERROR", "Invalid argument for Print value = (" + value + ")");
                }
            }
            else {
                //print the most recent run
                Simulation.execute("PRINT", event.printResults());
            }
        }

    }
    private void export(String value){
        //checking whether event run exists to be exported
        if(!eventList.isEmpty() && (curState == State.EVENT || curState == State.ON)){
            //get all the runs if value is null
            if(value == null){
                    Simulation.export(event.sendRuns());
            }
            //else get run from value given
            else{
                try {
                    Event latest = eventList.get(eventList.size() - 1);
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
    private void clear(String bibNumber){
        if(event != null){
            if(bibNumber == null){
                event.clear();
            }
            else {
                try{
                    event.clear(Integer.parseInt(bibNumber));

                } catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void endRun(){
        if(runCalled && event!= null){
            eventList.add(event);
            event.saveRun();

            client.sendRun(event.getLastRun());

            event.clear();
            curState = State.ON;
            runCalled = false;
            eventCalled = false;
        }
    }
    private void dnf(){
        if((runCalled && eventCalled)){
            //assign the next up racer the DNF tag represented by -1 right now
            event.dnf();
        }
    }
   private void swap(){
        if(runCalled) {
            event.swap();
        }
   }
   private void connect(String sensorName, String channelNumber){
       try{
           int chanNum = Integer.parseInt(channelNumber) -1;
           if(chanNum >= 8)
            throw new NumberFormatException();

           Sensor s = SensorFactory.makeSensor(sensorName);
           if(s == null)
               Simulation.execute("Error",sensorName +" is not a valid sensor");
           channels[chanNum].connectSensor(s);
       }
       catch(NumberFormatException ex){
           Simulation.execute("Error",channelNumber +" is not a valid number");
       }
   }

   public String getResults(){
       String data = "INPROGRESS:\n";
       data += event.getData("running");
       data +="\n\nFINISHED:\n";
       data += event.getData("finished");
        Simulation.execute("Print", data);

       return data;

   }
   public String getElapsedTime(){
       if(event != null)
        return event.getData("running");

       return null;
   }
   public String getQueue(){
       if(event != null)
           return event.getData("queue");

       return null;
   }

   public String getFinishedTime(){
       if(event != null)
           return event.getData("finished");

       return null;
   }
    public Time getSysTime()
    {
        return sysTime;
    }
    public String getState(){return curState.toString();}

}
