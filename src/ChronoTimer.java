import Util.Time;

import java.util.ArrayList;

public class ChronoTimer {
    private State curState;
    private Time sysTime;
    private Event event;
    private Channel channels[];
    private ArrayList<Event> eventList;     //used to store all the previous events


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

    public void execute(String command, String value){
       Simulation.execute("PRINT",sysTime.getSysTime() + " COMMAND: "+ command + " STATE: " + curState.toString());
        switch(command)
        {
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
                     event = new Event(channels);
                     curState = State.EVENT;
                }
                break;
            case "NEWRUN":
                if(curState.equals(State.EVENT)){
                    curState = State.RUN;
                }
                break;
            case "TOG":
                if(curState.equals(State.RUN)){
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
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.RUN)){
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
                        event.setStartTime(channels[channelNum-1].triggerSensor());
                        curState = State.INPROGRESS;
                    }
                    //only finish if the there was already a start
                    else if(curState.equals(State.INPROGRESS))
                        event.setFinishTime(channels[channelNum-1].triggerSensor());
                }
                break;
            //same as TRIG 1
            case "START":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS)){
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
                    System.out.println(event.printResults());
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
                    System.out.println("EXITING SIMULATOR...");
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
    public void execute(String time, String command, String value){
        Simulation.execute("PRINT",sysTime.getSysTime() + " COMMAND: "+ command + " STATE: " + curState.toString());
        switch(command)
        {
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
                    event = new Event(channels);
                    curState = State.EVENT;
                }
                break;
            case "NEWRUN":
                if(curState.equals(State.EVENT)){
                    curState = State.RUN;
                }
                break;
            case "TOG":
                if(curState.equals(State.RUN)){
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
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.RUN)){
                    event.addRacer(Integer.parseInt(value));
                    //dont change the state because may need to enter multiple racers.
                }
                break;
            case "TRIG":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS)){
                    int channelNum = Integer.parseInt(value);

                    //if it is odd then it is the start
                    if((channelNum % 2) != 0) {
                        event.setStartTime(Time.StringToMilliseconds(time));
                        curState = State.INPROGRESS;
                    }
                    //only finish if the there was already a start
                    else if(curState.equals(State.INPROGRESS))
                        event.setFinishTime(Time.StringToMilliseconds(time));
                }
                break;
            //same as TRIG 1
            case "START":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS)){
                    event.setStartTime(Time.StringToMilliseconds(time));
                    curState = State.INPROGRESS;
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(curState.equals(State.INPUTRACERS)){
                    event.setFinishTime(Time.StringToMilliseconds(time));
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
                    System.out.println(event.printResults());
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
                    System.out.println("EXITING SIMULATOR...");
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
    //method for channel error checking

    //method for reseting all the fields for RESET
    private void clearFields(){
        curState = State.OFF;
        channels = new Channel[8];
        eventList = new ArrayList<>();
        event = null;
    }
    public Time getSysTime()
    {
        return sysTime;
    }
}
