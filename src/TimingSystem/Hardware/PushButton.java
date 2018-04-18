package TimingSystem.Hardware;

public class PushButton extends Sensor {


   public PushButton(){

   }

    @Override
     long trigger() {
        return System.currentTimeMillis();
    }
}
