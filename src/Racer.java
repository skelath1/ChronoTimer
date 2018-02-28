public class Racer {
    private long _startTime;
    private long _finishTime;

    private int _bibNumber;

    public Racer(int bibNumber){
        _bibNumber = bibNumber;
        _startTime = -1;
        _finishTime = -1;
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

    public void setFinishTime(long _finishTime) {
        this._finishTime = _finishTime;
    }
}
