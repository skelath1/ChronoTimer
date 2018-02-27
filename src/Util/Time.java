package Util;
import java.util.Calendar;
import java.text.SimpleDateFormat;
public class Time {

    private Calendar _cal;
    private int[] offsetArray;


    public Time(){
        offsetArray = new int[4];
        for(int i = 0;i < offsetArray.length; i++)
            offsetArray[i] = 0;
        _cal = Calendar.getInstance();

    }
    /**
     * This method gets the current system time
     * Default is computer time unless changed
     * @return time: the current/set time in HH:mm:ss:XX
     */
    public String getSysTime(){
        _cal = Calendar.getInstance();
        _cal.add(Calendar.HOUR_OF_DAY, offsetArray[0]);
        _cal.add(Calendar.MINUTE, offsetArray[1]);
        _cal.add(Calendar.SECOND, offsetArray[2]);
        _cal.add(Calendar.MILLISECOND, offsetArray[3]);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SS");
        return timeFormat.format(_cal.getTime());
    }

    /**
     * This method changes the system time to what the user specifies (must input HH:mm:ss:XX or HH:mm:ss:X)
     * @param time: the users specified time
     */
    public void setSysTime(String time){
        String[] arr = splitComponents(time);
        //calculating offsets
        offsetArray[0] = Integer.parseInt(arr[0])-_cal.get(Calendar.HOUR_OF_DAY);
        offsetArray[1] = Integer.parseInt(arr[1])- _cal.get(Calendar.MINUTE);
        offsetArray[2] = Integer.parseInt(arr[2])-_cal.get(Calendar.SECOND);
        offsetArray[3] = Integer.parseInt(arr[3])-_cal.get(Calendar.MILLISECOND);
    }
    /**
     * This method returns the elapsed time (HH:mm:ss:SS) from the start and finish times (should be in milliseconds)
     * @param start: the start time
     * @param finish: the finish time
     * @return: a string with elapsed time
     */
    public static String getElapsed(long start, long finish){
        return new SimpleDateFormat("HH:mm:ss:SS").format(finish-start);
    }

    /**
     * This method takes in a string and converts it to a long.
     * @param str: a string with format HH:mm:ss.SS
     * @return a long value in milliseconds
     */
    public static long StringToMilliseconds(String str){
            String[] arr = splitComponents(str);
            //converting each component into milliseconds and summing up
            long milliseconds = Long.parseLong(arr[0]) * 3600000L
                                + Long.parseLong(arr[1]) * 60000
                                + Long.parseLong(arr[2]) * 1000
                                + Long.parseLong(arr[3]);
            return milliseconds;
    }

    /**
     * Takes a string and splits into time components
     * @param str timestamp
     * @return array of strings
     */
    private static String[] splitComponents(String str){
                String[] arr = str.split("\\.");
                String milli = arr[1];
                //hours, min, and sec split below
                String body = arr[0];
                String[] bodyParts = body.split(":");
                String hour = bodyParts[0];
                String min = bodyParts[1];
                String sec = bodyParts[2];
                String[] compArr = {hour, min, sec, milli};
                return compArr;
    }
}

