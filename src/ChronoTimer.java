import Util.Time;

import java.util.ArrayList;

public class ChronoTimer {
    private State curState;
    private Time sysTime;
    private Event event;
    private Channel channels[];
    private ArrayList<Event> eventList;     //used to store all the previous events


    public enum State{
        ON,OFF,EVENT,RUN,TOG1,TOG2,INPUTRACERS,INPROGRESS,ENDRUN
    }


    public ChronoTimer(){
        curState = State.OFF;
        channels = new Channel[8];
        eventList = new ArrayList<>();

    }

    public void execute(String command, String value){
        switch(command)
        {
            case "POWER":
                if(curState.equals(State.OFF)){
                    curState = State.ON;
                    //setting the interal clock
                    sysTime = new Time();
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
                    event.toggleChannel(Integer.parseInt(value));
                    curState = State.TOG1;
                }
                else if(curState.equals(State.TOG2)){
                    event.toggleChannel(Integer.parseInt(value));
                    curState = State.INPUTRACERS;
                }
                break;
            case "NUM":
                if(curState.equals(State.INPUTRACERS) ){
                    event.addRacer(Integer.parseInt(value));
                    //dont change the state because may need to enter multiple racers.
                }
                break;
            case "TRIG":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS)){
                    int channelNum = Integer.parseInt(value);

                    //if it is odd then it is the start
                    if((channelNum % 2) != 0) {
                        event.setStartTime(Time.getTime());
                        curState = State.INPROGRESS;
                    }
                    //only finish if the there was already a start
                    else if(curState.equals(State.INPROGRESS))
                        event.setFinishTime(Time.getTime());
                }
                break;
            //same as TRIG 1
            case "START":
                if(curState.equals(State.INPUTRACERS) || curState.equals(State.INPROGRESS)){
                    event.setStartTime(Time.getTime());
                    curState = State.INPROGRESS;
                }
                break;
            //same as TRIG 2
            case "FINISH":
                if(curState.equals(State.INPUTRACERS)){
                    event.setFinishTime(Time.getTime());
                }
                break;
            case "PRINT":
                if(curState.equals(State.INPROGRESS)){
                    //print the results of the race
                }
                break;
            case "ENDRUN":
                if(curState.equals(State.INPROGRESS)){
                    //go to the ON state when the run is over so that there can be another run
                    curState = State.ON;
                    //do you print after the event is over
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
                   //put the race back in the queue at the beginning
                    event.getRacer();
                }
                break;
            case "EXIT":
                if(curState.equals(State.INPROGRESS)){
                    //just reset the fields so its like restarting at
                    clearFields();
                    //print that you exited the simulator?

                }
                break;



        }

    }
    //method for channel error checking
    //method for reseting all the fields? for EXIT
    private void clearFields(){
        curState = State.OFF;
        channels = new Channel[8];
        eventList = new ArrayList<>();
        event = null;
    }
}
