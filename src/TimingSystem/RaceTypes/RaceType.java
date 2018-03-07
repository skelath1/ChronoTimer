package TimingSystem.RaceTypes;

public interface RaceType{
    void addRacer(int bibNumber);
    void setStartTime(long startTime);
    void setFinishTime(long finishTime);
    void cancelRacer();
    void clear();
    String printResults();
}
