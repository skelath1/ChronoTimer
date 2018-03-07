package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Run;

public class PARIND implements RaceType {
    public PARIND(Channel[] channels){

    }

    @Override
    public void addRacer(int bibNumber) {

    }

    @Override
    public void setStartTime(long startTime) {

    }

    @Override
    public void setFinishTime(long finishTime) {

    }

    @Override
    public void cancelRacer() {

    }

    @Override
    public void clear() {

    }

    /**
     *
     * @return
     */

    @Override
    public Run saveRun(){
        return null;
    }

    @Override
    public String printResults() {
        return null;
    }

    @Override
    public String toString(){
        return "PARIND";
    }
}
