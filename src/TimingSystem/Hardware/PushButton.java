package TimingSystem.Hardware;

public class PushButton extends Sensor {
   private Channel _channel;

   public PushButton(Channel channel){
       _channel = channel;
   }

    @Override
    void trigger() {

    }
}
