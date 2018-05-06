package TimingSystem;

public class Racer {
    private long _startTime;
    private long _finishTime;
    private int _bibNumber;

    public Racer(int bibNumber){
        _bibNumber = bibNumber;
        _startTime = 0;
        _finishTime = 0;
    }

    public void setBibNumber(int _bibNumber) {
        this._bibNumber = _bibNumber;
    }

    public long getStartTime() {
        return _startTime;
    }

    public long getFinishTime() {
        return _finishTime;
    }

    public int getBibNumber() {
        return _bibNumber;
    }

    public void setStartTime(long _startTime) {
        this._startTime = _startTime;
    }

    public void setFinishTime(long _finishTime) { this._finishTime = _finishTime; }
}
