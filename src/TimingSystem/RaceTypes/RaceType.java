package TimingSystem.RaceTypes;

import TimingSystem.Run;

public interface RaceType{
    void addRacer(int bibNumber);
    void setStartTime(long startTime, int channelNum);
    void setFinishTime(long finishTime, int channelNum);
    void cancelRacer();
    void clear();
    void swap();
    Run saveRun();
    String printResults();
    String toString();
}
