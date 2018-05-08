package TimingSystem;

import Util.Time;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
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

    public void setTimeStamp(String timeStamp){ this.timeStamp = timeStamp;}

    public ArrayList<Result> getResults(){
        return results;
    }
    public String getStrResults(){
        int cnt = 0;
        String val = "";
        for(Result res: results){
            ++cnt;
            String time = res.get_time();
            System.out.println("time = " + time);
            if(time.equals("0")) {
                time = "00:00:00:00";
            } else if(time.equals("-1")){
                time = "DNF";
            }

            val +=(cnt + ": " + res.get_bib() + " " + time +"\n");
        }
        return val;
    }
}
