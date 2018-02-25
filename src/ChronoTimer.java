import Util.Time;

import java.util.ArrayList;

public class ChronoTimer {
    private State curState;
    private Time sysTime;
    private Event event;
    private Channel channels[];


    public enum State{
        ON,OFF,EVENT,RUN,TOG1,TOG2,INPUTRACERS,INPROGRESS,ENDRUN
    }


    public ChronoTimer(){
        curState = State.OFF;
        channels = new Channel[8];



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
                     event = new Event(value);
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
                if(curState.equals(State.INPUTRACERS) || event.ch){
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
                    else{
                        event.setFinishTime(Time.getTime());
                        curState = State.INPROGRESS;
                    }
                }
                break;
            case "PRINT":
                if(curState.equals(State.INPROGRESS)){
                    //print the results of the race
                }
                break;
            case "ENDRUN":
                if(curState.equals(State.INPROGRESS)){

                }
                break;


        }

    }
    //method for channel error checking
}
