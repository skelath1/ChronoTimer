package TimingSystem.Hardware;

public class PushButton extends Sensor {
   private Channel _channel;

   public PushButton(Channel channel){
       _channel = channel;
   }

    @Override
     long trigger() {
        return System.currentTimeMillis();
    }
}
