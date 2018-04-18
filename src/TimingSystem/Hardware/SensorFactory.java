package TimingSystem.Hardware;

public class SensorFactory {

    /**
     *
     * @param sensorName
     * @return Sensor if valid input otherwise null
     */
    public static Sensor makeSensor(String sensorName){
       sensorName = sensorName.toLowerCase();
       switch (sensorName){
           case("electriceye"):
               return new ElectricEye();
           case("gate"):
               return new Gate();
           case("pad"):
               return new Pad();
           case("pushbutton"):
               return new PushButton();
       }
       return null;
    }
}
