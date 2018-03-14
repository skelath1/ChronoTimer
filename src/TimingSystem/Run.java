package TimingSystem;

import Util.Time;

import java.util.ArrayList;
import java.util.Collection;

public class Run {
    private String timeStamp;
    private String eventType;
    private ArrayList<Result> results;

    public Run(String type){
        eventType = type;
        results = new ArrayList<>();
    }

    public void addResults(Collection<Racer> racers){
        for(Racer r : racers)
            results.add(new Result(r.getBibNumber(), Time.getElapsed(r.getStartTime(), r.getFinishTime())));
    }

    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }
}
