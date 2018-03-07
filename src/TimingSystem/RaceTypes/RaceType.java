package TimingSystem.RaceTypes;

import TimingSystem.Run;

public interface RaceType{
    void addRacer(int bibNumber);
    void setStartTime(long startTime);
    void setFinishTime(long finishTime);
    void cancelRacer();
    void clear();
    Run saveRun();
    String printResults();
    String toString();
}
