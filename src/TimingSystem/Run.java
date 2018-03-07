package TimingSystem;

import Util.Time;

import java.util.ArrayList;
import java.util.Collection;

public class Run {
    private String eventType;
    private Collection<Result> results;

    public Run(String type){
        eventType = type;
        results = new ArrayList<>();
    }

    public void addResults(Collection<Racer> racers){
        for(Racer r : racers)
            results.add(new Result(r.getBibNumber(), Time.getElapsed(r.getStartTime(), r.getFinishTime())));
    }
}
