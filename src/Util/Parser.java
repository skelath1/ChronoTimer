package Util;

public class Parser {
    /**
    * This methods breaks the string into an array
    * Depending on the string given
    * @param data: a string containing a command and maybe value
    * @return array: an array with the cmd and val
     */
    public String[] parse(String data){
        // [COMMAND, value] Make Command Uppercase
        String cmd;
        String val1;
        String[] s = new String[0];
        String[] strArray = data.split(" ");
        switch(strArray.length){
            case 1:
                cmd = strArray[0].toUpperCase();
                s = new String[1];
                s[0] = cmd;
                break;
            case 2:
                cmd = strArray[0].toUpperCase();
                val1 = strArray[1].toUpperCase();
                s = new String[2];
                s[0] = cmd;
                s[1] = val1;
                break;
        }
        return s;
    }

    /**
     * This method breaks the string (from a file) into an array
     * depending on the string given
     * @param data: a string containing a time, command, and possibly a value
     * @return arr: an array with time, cmd, possibly val
     */
    public String[] parseFile(String data) {
        String time;
        String cmd;
        String val1;
        String[] arr = new String[0];
        String[] inputArr = data.split(" ");
        switch(inputArr.length){
            case 2:
                time = inputArr[0];
                cmd = inputArr[1].toUpperCase();
                arr = new String[2];
                arr[0] = time;
                arr[1] = cmd;
                break;
            case 3:
                time = inputArr[0];
                cmd = inputArr[1].toUpperCase();
                val1 = inputArr[2].toUpperCase();
                arr = new String[3];
                arr[0] = time;
                arr[1] = cmd;
                arr[2] = val1;
                break;
        }
        return arr;
    }

}
