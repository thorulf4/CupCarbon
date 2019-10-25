package senscript.customCommand;

import d504.RelayConfig;
import device.SensorNode;
import senscript.Command;

public class Command_CREATERELAYCONFIG extends Command {

    String output="";

    public Command_CREATERELAYCONFIG(SensorNode node, String output){
    this.sensor = node;
    this.output = output;
    }


    @Override
    public double execute(){
        String id = sensor.getName();
        RelayConfig relayConfig = new RelayConfig(id, "0");
        String serializedRelayConfig = relayConfig.serialize();
        sensor.getScript().putVariable(output,serializedRelayConfig);
        return 0;
    }
}
