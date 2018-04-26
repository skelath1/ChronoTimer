package TimingSystem.RaceTypes;

import TimingSystem.Hardware.Channel;
import TimingSystem.Racer;
import TimingSystem.Result;
import TimingSystem.Run;
import Util.Time;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class PARIND implements RaceType {
    private ArrayList<Run> runs;

    private Deque<Racer> _finished;
    private Deque<Racer> _racersL;
    private Deque<Racer> _racersR;

    private Deque<Racer> _left;
    private Deque<Racer> _right;

    private boolean isRight = false;
    private boolean addRight = false;
    private boolean inProg;


    public PARIND(){
        _finished = new LinkedList<>();
        _racersL = new LinkedList<>();
        _racersR = new LinkedList<>();
        _left = new LinkedList<>();
        _right = new LinkedList<>();
        runs = new ArrayList<>();
    }


    /**
     *
     * @param bibNumber
     */
    @Override
    public void addRacer(int bibNumber) {
        if(!validNewRacer(bibNumber)) return;
        if(!addRight){
            _racersL.add(new Racer(bibNumber));
            addRight = !addRight;

        } else{
            _racersR.add(new Racer(bibNumber));
            addRight = !addRight;

        }
    }

    private boolean validNewRacer(int bibNumber){
        for(Racer r : _racersL){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _left){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _racersR){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _right){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        for(Racer r : _finished){
            if(r.getBibNumber() == bibNumber)
                return false;
        }

        return true;
    }

//    /**
//     *
//     * @param startTime
//     * @param channelNum
//     */
//    @Override
//    public void setStartTime(long startTime, int channelNum) {
//        if(channelNum == 1){
//            inProg = true;
//            if(_racersL.isEmpty()) return;
//            Racer l = _racersL.removeFirst();
//            l.setStartTime(startTime);
//            _left.add(l);
//            isRight = true;
//        } else if(channelNum == 3){
//            inProg = true;
//            if(_racersR.isEmpty()) return;
//            Racer r = _racersR.removeFirst();
//            r.setStartTime(startTime);
//            _right.add(r);
//            isRight = false;
//        }
//    }
//
//    /**
//     *
//     * @param finishTime
//     * @param channelNum
//     */
//    @Override
//    public void setFinishTime(long finishTime, int channelNum) {
//        if(channelNum == 2){
//            if(!_left.isEmpty()){
//                Racer l = _left.removeFirst();
//                l.setFinishTime(finishTime);
//                _finished.add(l);
//                isRight = true;
//
//            }
//        } else if(channelNum == 4){
//            if(!_right.isEmpty()){
//                Racer r = _right.removeFirst();
//                r.setFinishTime(finishTime);
//                _finished.add(r);
//                isRight = false;
//            }
//        }
//        if(_left.isEmpty() && _right.isEmpty())
//            inProg = false;
//    }

    @Override
    public void setTime(long time, int channelNum) {
        switch(channelNum){
            case 1:
                inProg = true;
                if(_racersL.isEmpty()) return;
                Racer ls = _racersL.removeFirst();
                ls.setStartTime(time);
                _left.add(ls);
                isRight = true;
                break;
            case 2:
                if(!_left.isEmpty()){
                    Racer lf = _left.removeFirst();
                    lf.setFinishTime(time);
                    _finished.add(lf);
                    isRight = true;
                    if(_left.isEmpty() && _right.isEmpty())
                        inProg = false;
                }
                break;
            case 3:
                inProg = true;
                if(_racersR.isEmpty()) return;
                Racer rs = _racersR.removeFirst();
                rs.setStartTime(time);
                _right.add(rs);
                isRight = false;
                break;
            case 4:
                if(!_right.isEmpty()){
                    Racer rf = _right.removeFirst();
                    rf.setFinishTime(time);
                    _finished.add(rf);
                    isRight = false;
                    if(_left.isEmpty() && _right.isEmpty())
                        inProg = false;
                }
                break;
            default:

        }
    }

    /**
     *
     */
    @Override
    public void cancelRacer() {
        // Don't know racers position
        if(isRight){
            Racer l = _left.removeFirst();
            l.setStartTime(-1);
            _racersL.addFirst(l);
        } else{
            Racer r = _right.removeFirst();
            r.setStartTime(-1);
            _racersR.addFirst(r);
        }
    }

    /**
     *
     */
    @Override
    public void clear() {
        _finished.clear();
        _racersL.clear();
        _racersR.clear();
        _left.clear();
        _right.clear();
        isRight = false;
        addRight = false;
        inProg = false;
    }

    @Override
    public void clear(int bibNumber) {
        for(Racer r : _racersL){
            if(r.getBibNumber() == bibNumber) {
                _racersL.remove(r);
                break;
            }
        }

        for(Racer r : _racersR){
            if(r.getBibNumber() == bibNumber) {
                _racersR.remove(r);
                break;
            }
        }

        for(Racer r : _left){
            if(r.getBibNumber() == bibNumber) {
                _left.remove(r);
                break;
            }
        }

        for(Racer r : _right){
            if(r.getBibNumber() == bibNumber) {
                _right.remove(r);
                break;
            }
        }

        for(Racer r : _finished){
            if(r.getBibNumber() == bibNumber) {
                _finished.remove(r);
                break;
            }
        }
    }

    /**
     *
     */
    @Override
    public void swap() {
        //Not sure if there is anything for this
    }

    @Override
    public Run getLastRun() {
        return runs.get(runs.size()-1);
    }

    @Override
    public ArrayList<Run> getRuns() {
        return runs;
    }

    @Override
    public void dnf() {
        if(isRight){
            Racer l = _left.removeFirst();
            l.setStartTime(-1);
            _finished.addFirst(l);
        } else{
            Racer r = _right.removeFirst();
            r.setStartTime(-1);
            _finished.addFirst(r);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public void saveRun(){
        Run r = new Run(this.toString());
        r.addResults(_finished);
        runs.add(r);
    }

    /**
     *
     * @return
     */
    @Override
    public String printResults() {
        String s = "";
        long t = System.currentTimeMillis();
        if(inProg){
            for(Racer r : _racersL){
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) + "\n";
            }
            for(Racer r : _racersR){
                s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), t) + "\n";
            }
        } else {
            if(runs.isEmpty()){
                for(Racer r : _finished){
                    s += "TimingSystem.Racer: " + r.getBibNumber() + " : " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) +"\n";
                }
            }
            else {
                Run r = runs.get(runs.size() - 1);
                for (Result res : r.getResults()) {
                    s += "TimingSystem.Racer: " + res.get_bib() + " : " + res.get_time() + "\n";
                }
            }
        }
        return s;
    }

    @Override
    public String printResults(int runNumber) {
        String s = "";
        Run r = runs.get(runNumber-1);
        for(Result res : r.getResults()){
                s += "TimingSystem.Racer: " + res.get_bib() + " : " + res.get_time() + "\n";
        }
        return s;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return "PARIND";
    }

    @Override
    public String getData(String type) {
        String data = "";
        switch(type){
            case "queue":
                    return listToString(_racersL, _racersR, false);
            case "running":
                String s = "";
                long cTime = System.currentTimeMillis();
                for(Racer r : _left) {
                    s += r.getBibNumber() + " " + Time.getElapsed(r.getStartTime(), cTime) + " \n";
                }
                for(Racer r : _right) {
                    s += r.getBibNumber() + " " + Time.getElapsed(r.getStartTime(), cTime) + " \n";
                }
                return s;
            case "finished":
                return listToString(_finished, null, true);

            default:
        }
        return data;
    }

    private String listToString(Deque<Racer> list1, Deque<Racer> list2, boolean finished){
        String s = "";
        for(Racer r : list1) {
            if(finished)
                s += r.getBibNumber() + ": " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) + " \n";
            else
                s += r.getBibNumber() + "\n";
        }
        if(list2 != null) {
            for (Racer r : list2) {
                if (finished)
                    s += r.getBibNumber() + ": " + Time.getElapsed(r.getStartTime(), r.getFinishTime()) + " \n";
                else
                    s += r.getBibNumber() + "\n";
            }
        }
        return s;
    }

    private void printList(Deque<Racer> list){
        for(Racer r : list){
            System.out.println(r.getBibNumber());
        }
    }
}
