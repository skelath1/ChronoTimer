package Util;
import java.util.Calendar;
import java.text.SimpleDateFormat;
public class Time {

    private Calendar _cal;
    boolean getSetSysTime;
    public Time(){
        getSetSysTime = false;
    }
    /**
     * This method gets the current system time
     * Default is computer time unless changed
     * @return time: the current/set time in HH:mm:ss:XX
     */
    public String getSysTime(){
       // String time = _cal.get(Calendar.HOUR_OF_DAY)+":"+ _cal.get(Calendar.MINUTE)+":"+ _cal.get(Calendar.SECOND)+"."+ _cal.get(Calendar.MILLISECOND);
        //if(getSetSysTime)
         //   return time;
       // else
         //   return time.substring(0,time.length()-1);
        _cal  = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SS");
        return timeFormat.format(_cal.getTime());
    }

    /**
     * This method changes the system time to what the user specifies (must input HH:mm:ss:XX or HH:mm:ss:X)
     * @param time: the users specified time
     */
    public void setSysTime(String time){
        String[] arr = time.split("\\.");
        String milli = arr[1];
        //hours, min, and sec split below
        String body = arr[0];
        String[] bodyParts = body.split(":");
        String hour = bodyParts[0];
        String min = bodyParts[1];
        String sec = bodyParts[2];
        _cal.set(Calendar.HOUR, Integer.parseInt(hour));
        _cal.set(Calendar.MINUTE, Integer.parseInt(min));
        _cal.set(Calendar.SECOND, Integer.parseInt(sec));
        _cal.set(Calendar.MILLISECOND, Integer.parseInt(milli));
        getSetSysTime = true;
    }
    /**
     * This method returns the elapsed time from the start and finish times
     * @param start: the start time
     * @param finish: the finish time
     * @return: a string with elapsed time
     */
    public static String getElapsed(long start, long finish){

    }

}

