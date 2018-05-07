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
        //client = new ChronoClient();
    }

    /**
     * @param command String
     * @param value String
     * Takes in command from TimingSystem.Simulation and executes it.
     */
    public void execute(String command, String value,String value2){
        Simulation.execute("PRINT", " COMMAND: "+ command + "  VALUE: " + value);
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
                        if(channels[channelNum-1].isReady()) {
                            event.setTime(System.currentTimeMillis(),channelNum);
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
                        event.setTime(System.currentTimeMillis(),2);
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    if(channels[1].isReady())
                        event.setTime(System.currentTimeMillis(),2);
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
            case "DISC":
                disconnect(value);
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
        Simulation.execute("PRINT",time + "  COMMAND: "+ command + "  VALUE: " + value);
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
                                event.setTime(Time.stringToMilliseconds(time),channelNum);
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
                        event.setTime(Time.stringToMilliseconds(time),1);
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(runCalled){
                    if(channels[1].isReady())
                        event.setTime(Time.stringToMilliseconds(time),2);
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
            case "DISC":
                disconnect(value);
                break;

        }
    }
    /**
     * method for saving the run of an event
     */
    private void saveRun(){
        event.saveRun();
    }

    /**
     * turns the chrono timer on when the curState is off and turns off when curState is on
     */
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

    /**
     * sets runCalled to true
     * doesn't create a run here because event still could be called
     */
    private void newRun(){
        if(!curState.equals(State.OFF)){
            runCalled =true;
            if(client == null) client = new ChronoClient();
        }

    }

    /**
     * creates and event given the string param
     * if the param is null it creates a default event witch is IND
     * set eventCalled true so that you cant call event again during the run
     * @param value String
     */
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

    /**
     * toggles the channel given the channel number
     * @param value String
     */
    private void tog(String value){
        if(!curState.equals(State.OFF)){
            // too late to call event
            eventCalled = true;
            runCalled = true;
            if(event == null) {
                //creating a new default event if there wasn't one
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

    /**
     * just like power and power
     */
    private void reset(){
        runCalled = false;
        eventCalled = false;
        eventList = new ArrayList<>();
        event = null;
        curState = State.ON;
        for(Channel c: channels){
            c.setOff();
        }

    }

    /**
     * creates a racer with the value as a bib number
     * @param value String
     */
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

    /**
     * cancels the next up racer
     * marks the racer as DNF
     */
    private void cancel(){
        if(runCalled && eventCalled){
            //put the racer back in the queue at the beginning
            event.cancelRacer();
        }
    }

    /**
     * prints the current run if value is null otherwise prints the run from param
     * @param value String
     */
    private void print(String value){
        if(!(curState.equals(State.OFF))) {

            if (event != null) {

                if (value != null) {
                    try {
                        Simulation.execute("PRINT", event.printResults(Integer.parseInt(value)));

                    } catch (NumberFormatException nfm) {
                        Simulation.execute("ERROR", "Invalid argument for Print value = (" + value + ")");
                    }
                } else {
                    //print the most recent run
                    Simulation.execute("PRINT", event.printResults());
                }
            }
        }

    }

    /**
     * creates the run as a json string
     * exports all the runs if value === null
     * exports the run given the value
     * @param value String
     */
    private void export(String value){
        //checking whether event run exists to be exported
        if(!eventList.isEmpty() && !(curState.equals(State.OFF))){
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

    /**
     * exits the program
     */
    private void exit(){
        if(runCalled){
            Simulation.execute("EXIT",null);
        }
    }

    /**
     * clears a racer from the queue
     * if bibNumber == null clears all the racers from the run
     * @param bibNumber String
     */
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

    /**
     * ends the current run and sets up for the next one
     */
    private void endRun(){
        if(runCalled && event!= null){
            if(client != null)
                client.clear();

            eventList.add(event);
            event.saveRun();
            if(client != null)
                client.sendRun(event.getLastRun());

            event.clear();
            curState = State.ON;
            runCalled = false;
            eventCalled = false;
        }
    }

    /**
     * marks the next racer as DNF
     */
    private void dnf(){
        if((runCalled && eventCalled)){
            event.dnf();
        }
    }

    /**
     * swaps the racers
     */
   private void swap(){
        if(runCalled) {
            event.swap();
        }
   }

    /**
     * connects and creates sensors to the given channel
     * @param sensorName String
     * @param channelNumber String
     */
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
    private void disconnect(String channelNumber){
        try{
            int chanNum = Integer.parseInt(channelNumber) -1;
            if(chanNum >= 8)
                throw new NumberFormatException();

            channels[chanNum].disconnectSensor();
        }
        catch(NumberFormatException ex){
            Simulation.execute("Error",channelNumber +" is not a valid number");
        }
    }

    /**
     * gets the results of the current run
     * @return formatted string for the GUI
     */
   public String getResults(){
       //need to check if the run is over and then get the most recent run if it is
       if(!runCalled && !eventCalled){
           //get the previous run that has ended
           int index = eventList.size()-1;
           if(index >= 0) {
               String data = "INPROGRESS:\n";

               data +="\n\nFINISHED:\n";
               data += event.getLastRun().getStrResults();

               //Simulation.execute("Print", data);
               return data;
           }
           else{
               //print out nothing
               String data = "INPROGRESS:\n";
               data +="\n\nFINISHED:\n";
               return data;
           }
       }
       //printing out the current race
       String data = "INPROGRESS:\n";
       data += event.getData("running");
       data +="\n\nFINISHED:\n";
       data += event.getData("finished");
       //Simulation.execute("Print", data);

       return data;
   }
    public String getResults(String runNumber){
        //need to check if the run is over and then get the most recent run if it is
        try{
            int runNum = Integer.parseInt(runNumber) -1;
            if(runNum >= 0 && runNum < event.getRuns().size()) {
                String data = "INPROGRESS:\n";
                data +="\n\nFINISHED:\n";

                //get specific run
                data += event.getRuns().get(runNum).getStrResults() + "\n";
                //Simulation.execute("Print", data);
                return data;
            }

        }catch(NumberFormatException ex){
            Simulation.execute("ERROR","Invalid run number.");
        }
        String data = "INPROGRESS:\n";
        data +="\n\nFINISHED:\n";
        return data;
    }

    /**
     *
     * @return String of the current running racers
     */
   public String getElapsedTime(){
       if(event != null)
        return event.getData("running");

       return null;
   }

    /**
     *
     * @return String of current queue
     */
   public String getQueue(){
       if(event != null)
           return event.getData("queue");

       return null;
   }

    /**
     *
     * @return String gets finished times of the racers
     */
   public String getFinishedTime(){
       if(event != null)
           return event.getData("finished");
       return null;
   }

    /**
     *
     * @return the curState as a String
     */
    public String getState(){return curState.toString();}

}
