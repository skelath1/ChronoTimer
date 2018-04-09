package TimingSystem;

public class Result {
    private int _bib;
    private String _time;

    public Result(int bib, String time){
        _bib = bib;
        _time = time;
    }

    public int get_bib() {
        return _bib;
    }

    public String get_time(){
        return _time;
    }
}
