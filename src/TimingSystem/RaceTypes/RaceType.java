package TimingSystem.RaceTypes;

import TimingSystem.Run;

import java.util.ArrayList;

public interface RaceType{
    void addRacer(int bibNumber);
    void setStartTime(long startTime, int channelNum);
    void setFinishTime(long finishTime, int channelNum);
    void cancelRacer();
    void clear();
    void clear(int bibNumber);
    void swap();
    void saveRun();
    String printResults();
    String printResults(int runNumber);
    String toString();
    String getData(String type);
    ArrayList<Run> getRuns();

}
